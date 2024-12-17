package com.zena.authserver.domain;

import com.zena.authserver.dto.UserRequest;
import com.zena.authserver.entity.User;
import com.zena.authserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JpaOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserRequest userRequest) {
        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .build();
        userRepository.save(user);
    }
}
