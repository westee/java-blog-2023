package hello.controller;

import hello.entity.Result;
import hello.entity.User;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class AuthController {
    UserService userService;

    AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping("/")
    public User getUser(@RequestParam("id") Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/auth")
    public Result getStatus() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(Objects.equals(name, "anonymousUser")) {
            return new Result("fail", "未登录", false);
        } else {
            return new Result("true", "已经登录", true, userService.getUserByUsername(name));
        }
    }

    @PostMapping("/auth/login")
    public Result doLogin(@RequestBody UserNameAndPassword userNameAndPassword) {
        String username = userNameAndPassword.getUsername();
        String password = userNameAndPassword.getPassword();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (Exception e) {
            return new Result("fail", e.getMessage(), false);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

        try {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            authenticationManager.authenticate(token);
            context.setAuthentication(token);
            SecurityContextHolder.setContext(context);
            return new Result("ok", "登录成功", true, userService.getUserByUsername(username));
        }
        catch (Exception e) {
            return new Result("fail", "用户不存在或密码不正确", false);
        }
    }

    @PostMapping("/auth/register")
    public Result insertUser(@RequestBody UserNameAndPassword userNameAndPassword) {
        if(Objects.isNull(userNameAndPassword.getPassword()) && Objects.isNull(userNameAndPassword.getUsername()) ) {
            return Result.fail("参数错误");
        }
        try {
            userService.save(userNameAndPassword.getUsername(), userNameAndPassword.getPassword());
            return Result.success("注册成功");
        } catch (DuplicateKeyException e) {
            return Result.fail("用户名重复");
        }
    }

    @GetMapping("/auth/logout")
    public Result logout() {
        if(Objects.equals(SecurityContextHolder.getContext().getAuthentication().getName(), "anonymousUser")) {
            return Result.fail("用户尚未登录");
        } else {
            SecurityContextHolder.clearContext();
            return Result.success("用户尚未登录");
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
