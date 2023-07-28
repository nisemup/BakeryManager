package com.nisemup.bakerymanager.security;

import com.nisemup.bakerymanager.model.Employees;
import com.nisemup.bakerymanager.model.Status;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Collection;

@Data

public class SecurityEmployees implements UserDetails {

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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetails fromEmployee(Employees employee) {
        return new User(
                employee.getEmail(), employee.getPassword(),
                employee.getStatus().equals(Status.ACTIVE),
                employee.getStatus().equals(Status.ACTIVE),
                employee.getStatus().equals(Status.ACTIVE),
                employee.getStatus().equals(Status.ACTIVE),
                employee.getRole().getAuthorities()
        );
    }
}
