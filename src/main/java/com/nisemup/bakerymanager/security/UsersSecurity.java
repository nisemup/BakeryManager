package com.nisemup.bakerymanager.security;

import com.nisemup.bakerymanager.model.Users;
import com.nisemup.bakerymanager.model.Status;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Collection;

@Data

public class UsersSecurity implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromEmployee(Users users) {
        return new User(
                users.getEmail(), users.getPassword(),
                users.getStatus().equals(Status.ACTIVE),
                users.getStatus().equals(Status.ACTIVE),
                users.getStatus().equals(Status.ACTIVE),
                users.getStatus().equals(Status.ACTIVE),
                users.getRole().getAuthorities()
        );
    }
}
