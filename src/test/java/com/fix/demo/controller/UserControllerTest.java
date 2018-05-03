package com.fix.demo.controller;

import com.fix.demo.logic.user.User;
import com.fix.demo.logic.user.UserDTO;
import com.fix.demo.service.UserService;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    UserService mockService = mock(UserService.class);

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
    public void saveUser() throws Exception {

        UserDTO dto = new UserDTO("4028818a632413a801632413b7290000", "newuser");
        User user = new User("newuser", "newpass");

        when(mockService.save(user)).thenReturn(dto);

        MvcResult mvcResult = mockMvc.perform(put("/user2")
                .param("user", "newuser")
                .param("pass", "newpass"))
                .andReturn();

        Pattern pattern = Pattern.compile("\\{\\\"id\":\"+.*\"username\":\"newuser\"}");
        Matcher matcher = pattern.matcher(mvcResult.getResponse().getContentAsString());

        assertTrue(matcher.matches());
    }

}