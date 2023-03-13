package hello;

import hello.service.User;
import hello.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public User index() {
        return userService.getUser(1);
    }
}
