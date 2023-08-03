package com.nisemup.bakerymanager.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        UserDetails user = User.withUsername("testUser")
                .password(passwordEncoder.encode("testPass"))
                .roles("USER")
                .build();
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
    }

    @Test
    void testAuthentication() {
        String username = "testUser";
        String password = "testPass";
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);

        assertNotNull(authenticationResult);
        assertTrue(authenticationResult.isAuthenticated());
        assertEquals(username, authenticationResult.getName());
        assertTrue(authenticationResult.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
