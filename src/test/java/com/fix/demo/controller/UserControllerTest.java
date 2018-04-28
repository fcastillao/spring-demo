package com.fix.demo.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * if i am logged in, i should be able to query for the admin user
     *
     * @throws Exception
     */
    @Test
    @WithMockUser
    public void loggedFindUser() throws Exception {
        mockMvc.perform(get("/user").param("id", "X"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":\"X\",\"username\":\"admin\"}"));
    }

    @Test
    @WithMockUser
    public void users() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    @WithMockUser
    @Ignore
    public void saveUser() throws Exception {
    }

}