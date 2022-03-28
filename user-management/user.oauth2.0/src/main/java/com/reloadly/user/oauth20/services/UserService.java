package com.reloadly.user.oauth20.services;

import com.reloadly.user.oauth20.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/*
 * UserDetailsService is used by the DAO Authentication provider to load user details required for authentication
 */
@Service(value = "userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Incorrect username / password supplied");
        }
        var account = user.get().getAccount();

        return User
                .withUsername(username)
                .password(account.getPassword())
                .authorities(account.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
