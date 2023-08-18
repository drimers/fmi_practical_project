package com.stefanpetkov.medical.security.authentication;

import com.stefanpetkov.medical.domain.UserCredentials;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;


public class ApplicationUserDetailsImpl implements ApplicationUserDetails {


    private Long userId;
    private String email;
    private String password;
    private Set<? extends GrantedAuthority> grantedAuthorities;


    public ApplicationUserDetailsImpl(UserCredentials userCredentials) {
        this.userId = userCredentials.getBaseUser().getId();
        this.email = userCredentials.getEmail();
        this.password = userCredentials.getPassword();
        this.grantedAuthorities = userCredentials.getApplicationUserRole().getGrantedAuthorities();
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    //== fix, change or modify according the needs during development ==
    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }
    //== end of fix, change or modify according the needs during development ==


    @Override
    public String toString() {
        return "ApplicationUserDetails{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}