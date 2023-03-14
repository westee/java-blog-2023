package hello.service;

import hello.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private Map<String, String> userPasswords = new ConcurrentHashMap<>();

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.save("user", "password");
    }

    private void save(String username, String password) {
        userPasswords.put(username, bCryptPasswordEncoder.encode(password));
    }

    public String getPassword(String username){
        return userPasswords.get(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userPasswords.containsKey(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        String password = userPasswords.get(username);
        return new org.springframework.security.core.userdetails.User(username, password, Collections.emptyList());
    }

    public User getUserById(String id) {
        return null;
    }
}
