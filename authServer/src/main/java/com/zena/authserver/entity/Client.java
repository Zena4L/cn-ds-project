package com.zena.authserver.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String clientId;
    private String clientSecret;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<AuthenticationMethod> authenticationMethods;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<GrantType> grantTypes;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<RedirectUrl> redirectUris;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Scope> scopes;

    @OneToOne(mappedBy = "client")
    private ClientTokenSettings clientTokenSettings;

}
