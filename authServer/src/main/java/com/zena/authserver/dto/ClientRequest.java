package com.zena.authserver.dto;

import java.util.List;

public record ClientRequest(
        String clientSecret,
        String clientName,
        List<String> clientAuthenticationMethods,
        List<String> authorizationGrantTypes,
        List<String> redirectUris,
        List<String> postLogoutRedirectUris,
        List<String> scopes
) {
}
