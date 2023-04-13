package ru.job4j.url;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserStore users;

    public UserDetailsServiceImpl(UserStore users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = users.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.get().getUsername(), user.get().getPassword(), emptyList());
    }
}