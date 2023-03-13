package hello.config;

import hello.service.UserMapper;
import hello.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {

    @Bean
    public UserService userService(UserMapper userMapper) {
        return new UserService(userMapper);
    }
}
