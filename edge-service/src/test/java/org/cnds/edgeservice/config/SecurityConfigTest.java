package org.cnds.edgeservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.when;

//@WebFluxTest
//@Import(SecurityConfig.class)
class SecurityConfigTest {

//    @Autowired
//    private WebTestClient webClient;
//
//    @MockBean
//    private ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;
//
//    @Test
//    void whenLogoutAuthenticatedAndWithCsrfTokenThen302() {
//        // Mock the reactiveClientRegistrationRepository response
//        when(reactiveClientRegistrationRepository.findByRegistrationId("test"))
//                .thenReturn(Mono.just(testClientRegistration()));
//
//        // Simulate a logged-in user with CSRF protection
//        webClient
//                .mutateWith(SecurityMockServerConfigurers.csrf()) // Ensure CSRF is configured
//                .mutateWith(SecurityMockServerConfigurers.mockOidcLogin()) // Simulate login
//                .post()
//                .uri("/logout")
//                .exchange()
//                .expectStatus().isFound(); // Expect HTTP 302 redirect
//    }
//
//    private ClientRegistration testClientRegistration() {
//        return ClientRegistration.withRegistrationId("test")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .clientId("test")
//                .clientSecret("secret")
//                .authorizationUri("https://sso.polarbookshop.com/auth")
//                .tokenUri("https://sso.polarbookshop.com/token")
//                .redirectUri("https://polarbookshop.com")
//                .build();
//    }
}
