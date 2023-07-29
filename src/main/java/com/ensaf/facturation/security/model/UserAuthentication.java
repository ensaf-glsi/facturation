package com.ensaf.facturation.security.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor @AllArgsConstructor
public class UserAuthentication {
    private String username;
    private List<String> roles;
}
