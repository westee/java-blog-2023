package hello.service;

public class UserService {
    UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUser(Integer id) {
        return userMapper.getUser(id);
    }
}
