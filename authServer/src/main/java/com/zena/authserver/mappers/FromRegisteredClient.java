package com.zena.authserver.mappers;

import com.zena.authserver.entity.AuthenticationMethod;
import com.zena.authserver.entity.Client;
import com.zena.authserver.entity.GrantType;
import com.zena.authserver.entity.RedirectUrl;
import com.zena.authserver.entity.Scope;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@AllArgsConstructor
public class FromRegisteredClient {
    private final Client client;

    public RegisteredClient fromClient() {
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(clientAuthenticationMethods(client.getAuthenticationMethods()))
                .authorizationGrantTypes(authorizationGrantTypes(client.getGrantTypes()))
                .scopes(scopes(client.getScopes()))
                .redirectUri(redirectUris(client.getRedirectUris()))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(client.getClientTokenSettings().getAccessTokenTTL()))
                        .accessTokenFormat(new OAuth2TokenFormat(client.getClientTokenSettings().getType()))
                        .build()).build();

    }

    private String redirectUris(List<RedirectUrl> redirectUris) {
        return redirectUris.stream()
                .map(RedirectUrl::getUrl)
                .findFirst()
                .orElse(null);
    }

    private Consumer<Set<String>> scopes(List<Scope> scopes) {
        return m -> {
            for (Scope scope : scopes) {
                m.add(scope.getScope());
            }
        };
    }

    private Consumer<Set<AuthorizationGrantType>> authorizationGrantTypes(List<GrantType> grantTypes) {
        return m -> {
            for (GrantType grantType : grantTypes) {
                m.add(new AuthorizationGrantType(grantType.getGrantType()));
            }
        };
    }

    private Consumer<Set<ClientAuthenticationMethod>> clientAuthenticationMethods(List<AuthenticationMethod> authenticationMethods) {
        return m -> {
            for (AuthenticationMethod method : authenticationMethods) {
                m.add(new ClientAuthenticationMethod(method.getAuthenticationMethod()));
            }
        };
    }
}
