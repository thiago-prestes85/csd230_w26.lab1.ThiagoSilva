package csd230.lab1.services;

import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.UserEntityRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserEntityRepository userRepo;
    private final CartEntityRepository cartRepo;
    private final PasswordEncoder passwordEncoder;

    public AppUserDetailsService(UserEntityRepository userRepo,
                                 CartEntityRepository cartRepo,
                                 PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserEntity registerNewUser(String username, String rawPassword) {

        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));


        user.setRole("ROLE_USER");

        UserEntity savedUser = userRepo.save(user);

        CartEntity cart = new CartEntity();
        cart.setUser(savedUser);
        cartRepo.save(cart);

        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
