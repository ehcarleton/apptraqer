package com.apptraqer.apptraqerauthservice.util;

import com.apptraqer.apptraqerauthservice.model.AtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class JwtTokenService {

    // Either a 256-bit secret key represented as:
    // - A 64-character hexadecimal string (each hex character represents 4 bits, so 64 hex chars = 256 bits), or
    // - A Base64-encoded string representing a 256-bit secret key.
    private final String secretKeyPath;

    public JwtTokenService() {
        this.secretKeyPath = "/run/secrets/jwt_secret_key"; // Default for production
    }

    // Constructor for testing or custom configuration
    public JwtTokenService(String secretKeyPath) {
        this.secretKeyPath = secretKeyPath;
    }

    private SecretKey getSignInKey() {
        try {
            String secretKey = Files.readString(Paths.get(secretKeyPath)).trim();

            // Determine if the key is Base64 or Hexadecimal
            byte[] keyBytes;
            if (isHexadecimal(secretKey)) {
                keyBytes = javax.xml.bind.DatatypeConverter.parseHexBinary(secretKey);
            } else if (isBase64(secretKey)) {
                keyBytes = Decoders.BASE64.decode(secretKey);
            } else {
                throw new IllegalArgumentException("Invalid secret key format. Must be Base64 or Hexadecimal.");
            }

            // Generate the HMAC key
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load secret key", e);
        }
    }

    boolean isBase64(String str) {
        // A simple check for Base64 encoding
        return str.matches("^[A-Za-z0-9+/=]+$") && str.length() % 4 == 0;
    }

    boolean isHexadecimal(String str) {
        // A simple check for hexadecimal format
        return str.matches("^[A-Fa-f0-9]+$");
    }

    public String generateToken(AtUser user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSignInKey()).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token); // parseClaimsJws still valid with updated parserBuilder
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}
