package com.zena.authserver.domain;

import com.zena.authserver.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query("select c from Client c where c.clientId = :clientId")
    Optional<Client> findByClientId(String clientId);

}
