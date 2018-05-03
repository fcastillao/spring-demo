package com.fix.demo.service;

import com.fix.demo.logic.user.User;
import com.fix.demo.logic.user.UserDTO;
import com.fix.demo.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private UserRepository userRepository = mock(UserRepository.class);

    @Autowired
    private UserService userService;


    @Test
    public void findByBadUsernameReturnsNull() throws Exception {
        when(userRepository.findByUsername("wrong")).thenReturn(null);
        UserDTO wrong = userService.findByUsername("wrong");
        assertEquals(null, wrong);
    }

    @Test
    public void save() throws Exception {
        UserDTO dto = new UserDTO("Y", "user");
        User user = new User("user", "9");

        when(userRepository.save(user)).thenReturn(user);

        UserDTO save = userService.save(user);
        assertEquals(dto.getUsername(), save.getUsername());
    }

    @Test
    public void findByIdXReturnsAdmin() throws Exception {
        User user = new User("X", "admin", "root");
        when(userRepository.findById("X")).thenReturn(java.util.Optional.of(user));
        //doReturn(user).when(userRepository.findById("X"));
        UserDTO x = userService.findById("X");
        assertEquals(user.getUsername(), x.getUsername());
        assertEquals(user.getId(), x.getId());
    }

    @Test
    public void findByIdBadReturnsNull() {
        when(userRepository.findById("bad")).thenReturn(null);
        UserDTO byId = userService.findById("bad");
        assertNull(byId);
    }

}