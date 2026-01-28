package com.travel.service;

import com.travel.entity.Role;
import com.travel.entity.User;
import com.travel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerClient(String name, String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ROLE_CLIENT);

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public String createResetToken(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("Email not found"));

    String token = UUID.randomUUID().toString();
    user.setResetToken(token);
    user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30)); // 30 min expiry
    userRepository.save(user);

    return token;
    }

    public void resetPassword(String token, String newPassword) {
    User user = userRepository.findByResetToken(token)
        .orElseThrow(() -> new IllegalStateException("Invalid token"));

    if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
        throw new IllegalStateException("Token expired");
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    user.setResetToken(null);
    user.setResetTokenExpiry(null);
    userRepository.save(user);
    }

}
