package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    private MockMvc mvc;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authentication;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(userService, authentication)).build();
    }

    @Test
    void doLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/auth"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    System.out.println(result);
                    Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("未登录"));
                });

        Map<String, String> usernameAndPassword = new HashMap<>();
        usernameAndPassword.put("username", "123");
        usernameAndPassword.put("password", "123");

        Mockito.when(userService.loadUserByUsername("123"))
                .thenReturn(new User("123", bCryptPasswordEncoder.encode("123"), Collections.emptyList()));
        Mockito.when(userService.getUserByUsername("123"))
                .thenReturn(new hello.entity.User(1, "123", bCryptPasswordEncoder.encode("123")));

        MvcResult response = mvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .content(new ObjectMapper().writeValueAsString(usernameAndPassword)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("登录成功")))
                .andReturn();

        HttpSession session = response.getRequest().getSession();

        mvc.perform(MockMvcRequestBuilders.get("/auth").session((MockHttpSession) session)).andExpect(status().isOk())
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("user")));
    }

    @Test
    void notLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/auth")).andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("未登录")));

    }
}