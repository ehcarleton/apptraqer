package com.apptraqer.apptraqerauthservice.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.apptraqer.apptraqerauthservice.model.AtUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.Base64;

class JwtTokenServiceTest {
    private Path hexStringPath;
    private Path base64StringPath;
    private Path badDataStringPath;

    // Static values for testing
    private static final String HEX_STRING = "f47c4bb9eecd4f56a0a723e4739a9e50b832ab3853f94d71b2b79d7eb3c0ad49f47c4bb9eecd4f56a0a723e4739a9e50b832ab3853f94d71b2b79d7eb3c0ad49";
    private static final String badData_string = "TGF0aW5nI{FoZSB0aW1l}HN0cnVjdHVyZSBp%iBzb21lIG5vbmFtZXMgYXMgc3VjaCBhcmVhcyBmb3IgdGhlIGF";

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary file for the secret
        hexStringPath = Files.createTempFile("jwt_secret_key_hex", ".txt");
        Files.writeString(hexStringPath, HEX_STRING);

        base64StringPath = Files.createTempFile("jwt_secret_key_base64", ".txt");
        Files.writeString(base64StringPath, Base64.getEncoder().encodeToString(new SecureRandom().generateSeed(32)));

        badDataStringPath = Files.createTempFile("jwt_secret_key_badData", ".txt");
        Files.writeString(badDataStringPath, badData_string);
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        AtUser user = new AtUser();
        user.setUsername("testUser");

        var jwtTokenService = new JwtTokenService(base64StringPath.toString());

        String token = jwtTokenService.generateToken(user);
        assertNotNull(token, "Token should not be null");
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        AtUser user = new AtUser();
        user.setUsername("testUser");
        var jwtTokenService = new JwtTokenService(base64StringPath.toString());

        String token = jwtTokenService.generateToken(user);
        assertTrue(jwtTokenService.validateToken(token), "Token should be valid");
    }

    @Test
    void getUsernameFromToken_ShouldExtractUsername() {
        AtUser user = new AtUser();
        user.setUsername("testUser");

        var jwtTokenService = new JwtTokenService(base64StringPath.toString());
        String token = jwtTokenService.generateToken(user);
        String username = jwtTokenService.getUsernameFromToken(token);

        assertEquals("testUser", username, "Username should match");
    }

    @Test
    void testGetSignInKeyBase64Format() throws Exception {
        // Create a spy of the JwtTokenService to track method calls
        JwtTokenService spyService = spy(new JwtTokenService(base64StringPath.toString()));

        // Using reflection to call the private method `getSignInKey`
        Method getSignInKeyMethod = JwtTokenService.class.getDeclaredMethod("getSignInKey");
        getSignInKeyMethod.setAccessible(true);

        // Call the method and get the key
        SecretKey key = (SecretKey) getSignInKeyMethod.invoke(spyService);

        // Verify that isBase64 was called and not isHexadecimal
        verify(spyService, times(1)).isBase64(anyString());
        verify(spyService, times(1)).isHexadecimal(anyString());

        // Verify the key is not null and the correct length
        assertNotNull(key, "The key should not be null.");
        assertEquals(256, key.getEncoded().length * 8, "The key length should be 256 bits.");
    }

    @Test
    void testGetSignInKeyHexFormat() throws Exception {
        // Create a spy of the JwtTokenService to track method calls
        JwtTokenService spyService = spy(new JwtTokenService(hexStringPath.toString()));

        // Using reflection to call the private method `getSignInKey`
        Method getSignInKeyMethod = JwtTokenService.class.getDeclaredMethod("getSignInKey");
        getSignInKeyMethod.setAccessible(true);

        // Call the method and get the key
        SecretKey key = (SecretKey) getSignInKeyMethod.invoke(spyService);

        // Verify that isHexadecimal was called and not isBase64
        verify(spyService, times(1)).isHexadecimal(anyString());
        verify(spyService, times(0)).isBase64(anyString());

        // Verify the key is not null and the correct length
        assertNotNull(key, "The key should not be null.");
        assertEquals(512, key.getEncoded().length * 8, "The key length should be 512 bits.");
    }

    @Test
    void testGetSignInKeyBadDataFormat() throws Exception {
        // Create a spy of the JwtTokenService to track method calls
        var spyService = spy(new JwtTokenService(badDataStringPath.toString()));

        // Using reflection to call the private method `getSignInKey`
        var getSignInKeyMethod = JwtTokenService.class.getDeclaredMethod("getSignInKey");
        getSignInKeyMethod.setAccessible(true);

        // Call the method and get the key
        try {
            getSignInKeyMethod.invoke(spyService);
            fail("Expected IllegalArgumentException");
        } catch (InvocationTargetException e) {
            // Check if the cause is the expected IllegalArgumentException
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertEquals("Invalid secret key format. Must be Base64 or Hexadecimal.", e.getCause().getMessage());
        }

        // Verify that isBase64 was called and not isHexadecimal
        verify(spyService, times(1)).isBase64(anyString());
        verify(spyService, times(1)).isHexadecimal(anyString());
    }

}