package com.zena.authserver.dtos;

import java.util.List;

public record ClientRequest(
        String clientId,
        String clientSecret,
        List<String> clientAuthenticationMethods,
        List<String> authorizationGrantTypes,
        List<String> scopes,
        List<String> redirectUris
) {
}
