package com.apptraqer.apptraqerauthservice.service;

import com.apptraqer.apptraqerauthservice.model.AtUser;
import com.apptraqer.apptraqerauthservice.repository.AtUserRepository;
import com.apptraqer.apptraqerauthservice.util.JwtTokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AtUserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(AtUserRepository userRepository, JwtTokenService jwtTokenService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public String createUser(String email, String username, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        AtUser user = new AtUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));  // Hash the password
        userRepository.save(user);

        return jwtTokenService.generateToken(user);
    }

    public String authenticateUser(String username, String password) {

        AtUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return jwtTokenService.generateToken(user);
    }

    public String authenticateOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        AtUser user = userRepository.findByEmail(email);
        if (user == null) {
            user = new AtUser();
            user.setUsername(oAuth2User.getAttribute("name"));
            user.setEmail(email);
            user.setOauthProvider("google");
            userRepository.save(user);
        }

        return jwtTokenService.generateToken(user);
    }
}
