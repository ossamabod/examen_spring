package org.example.springfsexam.Service;

import org.example.springfsexam.Entity.UserEntity;
import org.example.springfsexam.Repository.UserRepository;
import org.springframework.security.core.userdetails.User; // Import Spring Security's User
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        System.out.println("User found: " + user.getUsername()); // Debugging line

        return User.builder()
            .username(user.getUsername())
            .password(user.getPassword())  // This should be ENCODED in the database!
            .roles(user.getRole())
            .build();
    }


    public UserEntity registerUser(String username, String password) {
        UserEntity newUser = UserEntity.builder()
            .username(username)
            .password(passwordEncoder.encode(password)) // Encrypt password
            .role("USER")
            .build();

        return userRepository.save(newUser);
    }
}
