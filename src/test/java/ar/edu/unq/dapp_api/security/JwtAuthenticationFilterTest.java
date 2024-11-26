package ar.edu.unq.dapp_api.security;

import ar.edu.unq.dapp_api.config.security.JwtAuthenticationFilter;
import ar.edu.unq.dapp_api.config.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Ensure a clean context before each test
    }

    @Test
    void doFilterInternalWithValidJwtSetsAuthentication() throws ServletException, IOException {
        String jwt = "valid.jwt.token";
        String email = "user@example.com";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtUtil.extractEmail(jwt)).thenReturn(email);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtUtil.validateToken(jwt, userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(authentication);
        assertTrue(authentication.getAuthorities().isEmpty());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternalWithInvalidJwtDoesNotSetAuthentication() throws ServletException, IOException {
        String jwt = "invalid.jwt.token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtUtil.extractEmail(jwt)).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtUtil.validateToken(jwt, userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternalWithoutAuthorizationHeaderProceedsWithoutAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternalWithExistingAuthenticationSkipsTokenValidation() throws ServletException, IOException {
        var authentication = new UsernamePasswordAuthenticationToken("user@example.com", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, never()).validateToken(anyString(), any(UserDetails.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
