package com.zena.authserver.mappers;

import com.zena.authserver.entity.Client;
import com.zena.authserver.entity.RedirectUrl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FromRedirectUrl {
    private final Client client;

    public RedirectUrl from(String url) {
        return RedirectUrl.builder()
                .client(client)
                .url(url)
                .build();
    }
}
