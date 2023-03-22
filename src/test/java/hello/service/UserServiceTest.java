package hello.service;

import hello.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserMapper userMapper;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserService userService;

    private final String username = "123";

    @Test
    void save() {
        UserService spy = Mockito.spy(userService);
        Mockito.when(bCryptPasswordEncoder.encode("123")).thenReturn("456");
        Mockito.when(spy.getAvatar()).thenReturn("avatar");
        spy.save(username, "123");
        Mockito.verify(userMapper).insertUser(username,"456", "avatar");
    }

    @Test
    void getUserById() {
        userService.getUserById(1);
        Mockito.verify(userMapper).getUser(1);
    }

    @Test
    void getUserByUsername() {
        userService.getUserByUsername(username);
        Mockito.verify(userMapper).getUserByUsername(username);
    }

    @Test
    void throwExceptionWhenUserNotFound() {
        Mockito.when(userMapper.getUserByUsername("123")).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("123"));
    }

    @Test
    void returnUserDetailWhenUserFound(){
        Mockito.when(userMapper.getUserByUsername("myUser"))
                .thenReturn(new User(123, "123", "456"));
        UserDetails userDetails = userService.loadUserByUsername("myUser");

        Assertions.assertEquals("myUser", userDetails.getUsername());
        Assertions.assertEquals("456", userDetails.getPassword());
    }
}