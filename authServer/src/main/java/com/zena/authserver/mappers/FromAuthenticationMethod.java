package com.zena.authserver.mappers;

import com.zena.authserver.entity.AuthenticationMethod;
import com.zena.authserver.entity.Client;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@AllArgsConstructor
public class FromAuthenticationMethod {
    private final Client client;
    private ClientAuthenticationMethod clientAuthenticationMethod;

    public AuthenticationMethod from() {
        return AuthenticationMethod.builder()
                .client(client)
                .authenticationMethod(clientAuthenticationMethod.getValue())
                .build();
    }
}
