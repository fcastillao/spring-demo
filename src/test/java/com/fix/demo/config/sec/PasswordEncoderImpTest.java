package com.fix.demo.config.sec;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncoderImpTest {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void encode() throws Exception {
        String encoded = passwordEncoder.encode("admin");
        assertEquals(encoded, "2520f4adb421ebcfa353dc85423b16f5c15a07e333bcb3c0d04809d1548ce88c981b4990cfb5583236869429e49e78703b7e6f1c1562c8f62e8f8d66339b619d");
    }

    @Test
    public void matches() throws Exception {
        boolean passwordMatches= passwordEncoder.matches("admin", "2520f4adb421ebcfa353dc85423b16f5c15a07e333bcb3c0d04809d1548ce88c981b4990cfb5583236869429e49e78703b7e6f1c1562c8f62e8f8d66339b619d");
        assertTrue(passwordMatches);
    }

}