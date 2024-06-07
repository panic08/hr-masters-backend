package ru.panic.mainservice.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserDetails(String id, String password) implements org.springframework.security.core.userdetails.UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
    }
}
