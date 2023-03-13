package hello.service;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.inject.Inject;

public class OrderService {
//    @Autowired
//    @Resource
    private final UserService userService;

    @Inject
    public OrderService(UserService userService) {
        this.userService = userService;
    }

    public void placeOrder() {
//        userService.setName("欸嘿嘿");
    }
}
