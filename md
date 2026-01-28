// package com.travel.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/auth")
// public class LoginController {

//     private final AuthenticationManager authenticationManager;

//     @Autowired // make sure Spring injects the AuthenticationManager bean
//     public LoginController(AuthenticationManager authenticationManager) {
//         this.authenticationManager = authenticationManager;
//     }

//     @PostMapping("/login")
//     public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//         try {
//             Authentication auth = authenticationManager.authenticate(
//                     new UsernamePasswordAuthenticationToken(
//                             request.getEmail(),
//                             request.getPassword()
//                     )
//             );
//             return ResponseEntity.ok("Login successful");
//         } catch (AuthenticationException e) {
//             return ResponseEntity.status(401).body("Invalid email or password");
//         }
//     }
// }

// // DTO
// class LoginRequest {
//     private String email;
//     private String password;

//     public String getEmail() { return email; }
//     public void setEmail(String email) { this.email = email; }

//     public String getPassword() { return password; }
//     public void setPassword(String password) { this.password = password; }
// }
