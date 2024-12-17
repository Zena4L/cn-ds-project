package com.zena.authserver.web;

import com.zena.authserver.domain.JpaOAuth2UserService;
import com.zena.authserver.domain.JpaRegisteredClientRepository;
import com.zena.authserver.dto.ClientRequest;
import com.zena.authserver.dto.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ClientRegisterController {
    private final JpaRegisteredClientRepository registeredClientRepository;
    private final JpaOAuth2UserService userService;

    @PostMapping("/register")
    public void registerClient(@RequestBody ClientRequest clientRequest) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(UUID.randomUUID().toString())
                .clientSecret(clientRequest.clientSecret())
                .clientName(clientRequest.clientName())
                .clientAuthenticationMethods(methods -> methods.addAll(
                        clientRequest.clientAuthenticationMethods().stream()
                                .map(ClientAuthenticationMethod::new)
                                .toList()
                ))
                .authorizationGrantTypes(types -> types.addAll(
                        clientRequest.authorizationGrantTypes().stream()
                                .map(AuthorizationGrantType::new)
                                .toList()
                ))
                .redirectUris(uris -> uris.addAll(clientRequest.redirectUris()))
                .postLogoutRedirectUris(uris -> uris.addAll(clientRequest.postLogoutRedirectUris()))
                .scopes(scopes -> scopes.addAll(clientRequest.scopes()))
                .clientSecretExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .clientIdIssuedAt(Instant.now())
                .build();

        registeredClientRepository.save(registeredClient);
    }

    @PostMapping("/user")
    public void registerUser(@RequestBody UserRequest request) {
        userService.createUser(request);
    }
}