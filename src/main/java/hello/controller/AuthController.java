package hello.controller;

import hello.entity.User;
import hello.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {
    UserService userService;

    AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping("/")
    public User getUser(@RequestParam("id") String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/auth")
    public Result getStatus() {
        return new Result("fail", "未登录", false);
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
        authenticationManager.authenticate(token);
        try {
            User user = new User(1, userDetails.getUsername());
            return new Result("ok", "登录成功", true, user);
        } catch (Exception e) {
            return new Result("fail", "用户不存在或密码不正确", false);
        }
    }

    private class Result {
        String status;
        String msg;
        boolean isLogin;
        Object data;

        public Result(String status, String msg, boolean isLogin) {
            this(status, msg, isLogin, null);
        }

        public Result(String status, String msg, boolean isLogin, Object data) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public void setLogin(boolean login) {
            isLogin = login;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
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
