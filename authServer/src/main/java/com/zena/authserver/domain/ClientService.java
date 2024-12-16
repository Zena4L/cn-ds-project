package com.zena.authserver.domain;

import com.zena.authserver.dtos.ClientRequest;
import com.zena.authserver.entity.Client;
import com.zena.authserver.mappers.FromAuthenticationMethod;
import com.zena.authserver.mappers.FromGrantType;
import com.zena.authserver.mappers.FromRedirectUrl;
import com.zena.authserver.mappers.FromRegisteredClient;
import com.zena.authserver.mappers.FromScope;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService implements RegisteredClientRepository {
    private final ClientRepository clientRepository;

    @Transactional
    public void registerClient(ClientRequest clientRequest) {

        RegisteredClient registeredClient = createRegisteredClient(clientRequest);
        Client client = new Client();
        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(clientRequest.clientSecret());
        client.setAuthenticationMethods(
                registeredClient.getClientAuthenticationMethods().stream()
                        .map(authenticationMethod -> new FromAuthenticationMethod(client, authenticationMethod).from())
                        .collect(Collectors.toList())
        );
        client.setGrantTypes(
                registeredClient.getAuthorizationGrantTypes().stream()
                        .map(authorizationGrantType -> new FromGrantType(client, authorizationGrantType).from())
                        .collect(Collectors.toList())
        );
        client.setRedirectUris(
                registeredClient.getRedirectUris().stream()
                        .map(redirectUrl -> new FromRedirectUrl(client).from(redirectUrl))
                        .collect(Collectors.toList())
        );

        client.setScopes(
                registeredClient.getScopes().stream()
                        .map(scope -> new FromScope(client).from(scope))
                        .collect(Collectors.toList())
        );

        clientRepository.save(client);
    }

    private RegisteredClient createRegisteredClient(ClientRequest clientRequest) {
        return RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientRequest.clientId())
                .clientSecret(clientRequest.clientSecret())
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
                .scopes(scopes -> scopes.addAll(clientRequest.scopes()))
                .redirectUris(uris -> uris.addAll(clientRequest.redirectUris()))
                .build();
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        // Implementation here
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<Client> client = clientRepository.findById(Integer.parseInt(id));
        return client.map(value -> new FromRegisteredClient(value).fromClient()).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<Client> client = clientRepository.findByClientId(clientId);
        return client.map(value -> new FromRegisteredClient(value).fromClient()).orElse(null);
    }
}
