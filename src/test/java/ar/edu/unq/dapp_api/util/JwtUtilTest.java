package ar.edu.unq.dapp_api.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "mysecretkeymysecretkeymysecretkeymysecretkey");
    }

    @Test
    void extractEmailReturnsCorrectEmail() {
        String token = jwtUtil.generateToken("john.doe@example.com");
        String email = jwtUtil.extractEmail(token);

        assertEquals("john.doe@example.com", email);
    }

    @Test
    void extractExpirationReturnsCorrectExpirationDate() {
        String token = jwtUtil.generateToken("john.doe@example.com");
        Date expiration = jwtUtil.extractExpiration(token);

        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void generateTokenCreatesValidToken() {
        String token = jwtUtil.generateToken("john.doe@example.com");

        assertNotNull(token);
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void validateTokenReturnsTrueForValidToken() {
        when(userDetails.getUsername()).thenReturn("john.doe@example.com");
        String token = jwtUtil.generateToken("john.doe@example.com");

        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void validateTokenReturnsFalseForInvalidToken() {
        when(userDetails.getUsername()).thenReturn("john.doe@example.com");
        String token = jwtUtil.generateToken("jane.doe@example.com");

        assertFalse(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void extractClaimReturnsCorrectClaim() {
        String token = jwtUtil.generateToken("john.doe@example.com");
        Claims claims = jwtUtil.extractAllClaims(token);

        assertNotNull(claims);
        assertEquals("john.doe@example.com", claims.getSubject());
    }
}