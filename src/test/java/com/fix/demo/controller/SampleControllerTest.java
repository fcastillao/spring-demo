package com.fix.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * trying to log in with good creds should log in correctly
     * @throws Exception
     */
    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("admin")
                .password("root");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("admin"));
    }

    /**
     * trying to log in with bad creds should be unauth
     * @throws Exception
     */
    @Test
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("invalid")
                .password("invalidpassword");

        mockMvc.perform(login)
                .andExpect(unauthenticated());
    }

    /**
     * sample should give 200 http
     * @throws Exception
     */
    @Test
    public void accessUnsecuredResourceThenOk() throws Exception {
        mockMvc.perform(get("/sample"))
                .andExpect(status().isOk());
    }

    /**
     * /hello should redirect to login
     * @throws Exception
     */
    @Test
    public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    /**
     * if i am logged in i should be ok on sample 2
     * @throws Exception
     */
    @Test
    @WithMockUser
    public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
        mockMvc.perform(get("/sample2"))
                .andExpect(status().isOk());
    }

    /**
     *the sample endpoint should be accessible and return the sample string
     * @throws Exception
     */
    @Test
    public void sampleIsOpen() throws Exception {
        mockMvc.perform(get("/sample"))
                .andExpect(content().string("Hello sample!"));
    }

    /***
     * trying to access sample 2 should redirect to login (302 redirection)
     * @throws Exception
     */
    @Test
    public void sample2RedirectsToLogin() throws Exception {
        mockMvc.perform(get("/sample2"))
                .andExpect(status().is3xxRedirection());
    }
}
