package org.cnds.edgeservice.domain;

import java.util.List;

public record User(
        String username,
        String firstName,
        String lastname,
        List<String> roles
) {
}
