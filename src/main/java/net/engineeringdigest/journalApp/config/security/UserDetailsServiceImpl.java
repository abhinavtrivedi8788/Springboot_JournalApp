package net.engineeringdigest.journalApp.config.security;

import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userEntity = userRepository.findByUserName(username);
        if (userEntity != null) {
            return User.builder()
                    .username(userEntity.getUserName())
                    .password(userEntity.getPassword())
                    .roles(userEntity.getRoles().toArray(new String[0]))
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }


    }
}
