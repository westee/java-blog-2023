package hello.controller;

import hello.entity.LoginResult;
import hello.entity.User;
import hello.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Objects;

@RestController
public class AuthController {
    UserService userService;

    AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping("/")
    public User getUser(@RequestParam("id") Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/auth")
    public LoginResult getStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  Objects.nonNull(authentication) ? authentication.getName() : "anonymousUser";

        if(username.equals("anonymousUser")) {
            return new LoginResult("fail", "未登录", false);
        } else {
            String name = authentication.getName();
            return LoginResult.success( "已经登录",  userService.getUserByUsername(name), true);
        }
    }

    @PostMapping("/auth/login")
    public LoginResult doLogin(@RequestBody UserNameAndPassword userNameAndPassword) {
        String username = userNameAndPassword.getUsername();
        String password = userNameAndPassword.getPassword();
        UserDetails userDetails;
        try {
            userService.getUserByUsername(username);
            userDetails = userService.loadUserByUsername(username);
        } catch (Exception e) {
            return new LoginResult("fail", e.getMessage(), false);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

        try {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            authenticationManager.authenticate(token);
            context.setAuthentication(token);
            SecurityContextHolder.setContext(context);
            return new LoginResult("ok", "登录成功", true, userService.getUserByUsername(username));
        }
        catch (Exception e) {
            return new LoginResult("fail", "用户不存在或密码不正确", false);
        }
    }

    @PostMapping("/auth/register")
    public LoginResult insertUser(@RequestBody UserNameAndPassword userNameAndPassword) {
        if(Objects.isNull(userNameAndPassword.getPassword()) && Objects.isNull(userNameAndPassword.getUsername()) ) {
            return LoginResult.fail("参数错误");
        }
        try {
            userService.save(userNameAndPassword.getUsername(), userNameAndPassword.getPassword());
            return LoginResult.success("注册成功", userService.getUserByUsername(userNameAndPassword.getUsername()), false);
        } catch (DuplicateKeyException e) {
            return LoginResult.fail("用户名重复");
        }
    }

    @GetMapping("/auth/logout")
    public LoginResult logout() {
        if(Objects.equals(SecurityContextHolder.getContext().getAuthentication().getName(), "anonymousUser")) {
            return LoginResult.fail("用户尚未登录");
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.success("ok","已退出登录", false);
        }
    }

    private static class UserNameAndPassword {
        private String username;
        private String password;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
