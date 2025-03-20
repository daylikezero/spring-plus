package org.example.expert.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoder() {
        String rawPassword = "password";

        String encodedPassword = passwordEncoder.encode(rawPassword);

        Assertions.assertNotNull(encodedPassword);

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        Assertions.assertTrue(matches);
    }
}
