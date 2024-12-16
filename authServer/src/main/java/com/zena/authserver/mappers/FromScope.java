package com.zena.authserver.mappers;

import com.zena.authserver.entity.Client;
import com.zena.authserver.entity.Scope;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FromScope {
    private final Client client;

    public Scope from(String scope) {
        return Scope.builder()
                .client(client)
                .scope(scope)
                .build();
    }
}
