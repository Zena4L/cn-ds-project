package com.zena.authserver.web;

import com.zena.authserver.domain.ClientService;
import com.zena.authserver.dtos.ClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/register")
    public void createClient(@RequestBody ClientRequest clientRequest) {
        clientService.registerClient(clientRequest);
    }
}
