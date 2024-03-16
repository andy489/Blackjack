package com.casino.blackjack.service;

import com.casino.blackjack.model.user.CustomUserDetails;
import com.casino.blackjack.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// NOTE: This is not annotated as @Service, because we will return it as a bean.
public class BlackjackUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BlackjackUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<CustomUserDetails> customUserDetails = userRepository.findByUsername(username)
//                .map(u -> {
//                    PathfinderUserDetails userDetails = mapper.toUserDetails(u);
//
//                    userDetails.setAuthorities(u.getRoles().stream().map(mapper::toGrantedAuthority)
//                            .toList());
//
//                    return userDetails;
//                });

//        return pathfinderUserDetails
//                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        return null;
    }
}
