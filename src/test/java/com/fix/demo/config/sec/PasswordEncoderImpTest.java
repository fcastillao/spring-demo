package com.fix.demo.config.sec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncoderImpTest {

    @Autowired
    private PasswordEncoderImp passwordEncoder;


    @Test
    public void encode() throws Exception {
        String encoded = passwordEncoder.encode("root");
        assertEquals("d2cc084bea35ca6c09943066c863eef419f28b87dd75b2a0e540aa028a57100a80b6f0bf27653055fbd124412da204994ff536b3ae22fa9a27d082da40a2ca45", encoded);
    }

    @Test
    public void matches() throws Exception {
        boolean passwordMatches = passwordEncoder.matches("root", "d2cc084bea35ca6c09943066c863eef419f28b87dd75b2a0e540aa028a57100a80b6f0bf27653055fbd124412da204994ff536b3ae22fa9a27d082da40a2ca45");
        assertTrue(passwordMatches);
    }

    @Test
    public void rootHashShouldBe3506402() {
        int i = "root".hashCode();
        assertEquals(3506402, i);
    }

}