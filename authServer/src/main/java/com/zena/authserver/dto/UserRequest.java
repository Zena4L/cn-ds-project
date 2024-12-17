package com.zena.authserver.dto;

public record UserRequest(
        String username,
        String firstName,
        String lastName,
        String password) {
}
