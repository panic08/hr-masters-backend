package ru.panic.mainservice.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    public UserDetails(String id) {
        this.id = id;
    }

    private String id;
    private final String password = "";
    private final Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
