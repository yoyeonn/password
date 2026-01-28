package com.travel.controller;

import com.travel.dto.ChangePasswordRequest;
import com.travel.dto.LoginRequest;
import com.travel.dto.LoginResponse;
import com.travel.dto.RegisterRequest;
import com.travel.dto.UserMeResponse;
import com.travel.entity.User;
import com.travel.security.JwtUtil;
import com.travel.service.CustomUserDetailsService;
import com.travel.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.travel.dto.ForgotPasswordRequest;
import com.travel.dto.ResetPasswordRequest;
import com.travel.service.BrevoEmailService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final BrevoEmailService brevoEmailService;

    public AuthController(UserService userService, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, BrevoEmailService brevoEmailService) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.brevoEmailService = brevoEmailService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest request) {
        try {
            userService.registerClient(
                    request.getName(),
                    request.getEmail(),
                    request.getPassword()
            );
            return ResponseEntity.ok("Client registered successfully");
        } catch (IllegalStateException ex) {
            // Return 400 Bad Request if email already exists
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

            if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }

            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            String token = jwtUtil.generateToken(userDetails.getUsername(), role);

            // ✅ Fetch the real user from DB to get the name
            User user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return ResponseEntity.ok(new LoginResponse(
                    token,
                    user.getName(),
                    user.getEmail(),
                    role
            ));

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> me(@RequestHeader("Authorization") String auth) {
        String token = auth.replace("Bearer ", "");
        String email = jwtUtil.getEmailFromToken(token);

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ResponseEntity.ok(new UserMeResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        ));
    }

    @PostMapping("/change-password")
public ResponseEntity<?> changePassword(
        @RequestHeader("Authorization") String auth,
        @RequestBody ChangePasswordRequest req
) {
    String token = auth.replace("Bearer ", "");
    String email = jwtUtil.getEmailFromToken(token);

    User user = userService.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe actuel incorrect");
    }

    user.setPassword(passwordEncoder.encode(req.getNewPassword()));
    userService.save(user);

    return ResponseEntity.ok("Mot de passe modifié");
}

@PostMapping("/forgot-password")
public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
    try {
        String token = userService.createResetToken(req.getEmail());
        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        // ✅ SEND REAL EMAIL
        brevoEmailService.sendResetPasswordEmail(req.getEmail(), resetLink);

        return ResponseEntity.ok("Un email de réinitialisation a été envoyé.");
    } catch (IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}


@PostMapping("/reset-password")
public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
  try {
    userService.resetPassword(req.getToken(), req.getNewPassword());
    return ResponseEntity.ok("Password reset successful");
  } catch (IllegalStateException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}


}
