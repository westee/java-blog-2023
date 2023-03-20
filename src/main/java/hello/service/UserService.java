package hello.service;

import hello.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserMapper userMapper;

    @Autowired
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public void save(String username, String password) {
        String avatar = getAvatar();
        userMapper.insertUser(username, bCryptPasswordEncoder.encode(password), avatar);
    }

    private static String getAvatar() {
        String[] members = {"icon-ange.svg", "icon-cat.svg", "icon-cry.svg", "icon-erm.svg", "icon-excited.svg", "icon-frown.svg", "icon-glasses-smirk.svg", "icon-kiss.svg", "icon-moustache2.svg", "icon-moustache.svg", "icon-ok.svg", "icon-smooch.svg", "icon-tongue.svg", "icon-unsure.svg", "icon-wondering.svg"};
        Random random = new Random();
        int index = random.nextInt(members.length);
        return "http://blog-avatar.sun-rising.net/" + members[index];
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new org.springframework.security.core.userdetails.User(username, user.getEncryptedPassword(), Collections.emptyList());
    }

    public User getUserById(Integer id) {
        return userMapper.getUser(id);
    }

    public User getUserByUsername(String name) {
        return userMapper.getUserByUsername(name);
    }
}
