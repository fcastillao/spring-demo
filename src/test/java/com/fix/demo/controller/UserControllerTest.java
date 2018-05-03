package com.fix.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
     * @throws Exception the exception the MockMvc can throw
     */
    @Test
    @WithMockUser
    public void loggedFindUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/user").param("id", "X"))
                .andExpect(status().isOk()).andReturn();

        assertEquals("{\"id\":\"X\",\"username\":\"admin\"}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void usersShouldReturnAppJson() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        assertEquals("[{\"id\":\"X\",\"username\":\"admin\"}]", mvcResult.getResponse().getContentAsString());
    }

    /**
     * i should be able to save an user via the api
     *
     * @throws Exception the exception the MockMvc can throw
     */
    @Test
    @WithMockUser
    @Transactional
    public void saveUser() throws Exception {

        MvcResult mvcResult = mockMvc.perform(put("/user2")
                .param("user", "newuser")
                .param("pass", "newpass"))
                .andReturn();

        Pattern pattern = Pattern.compile("\\{\\\"id\":\"+.*\"username\":\"newuser\"}");
        Matcher matcher = pattern.matcher(mvcResult.getResponse().getContentAsString());

        assertTrue(matcher.matches());
    }

}