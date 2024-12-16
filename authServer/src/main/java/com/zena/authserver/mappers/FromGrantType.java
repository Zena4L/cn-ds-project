package com.zena.authserver.mappers;

import com.zena.authserver.entity.Client;
import com.zena.authserver.entity.GrantType;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@AllArgsConstructor
public class FromGrantType {
    private final Client client;
    private AuthorizationGrantType authorizationGrantType;

    public GrantType from() {
        return GrantType.builder()
                .client(client)
                .grantType(authorizationGrantType.getValue())
                .build();
    }
}
