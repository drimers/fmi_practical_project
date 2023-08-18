package com.stefanpetkov.medical.security;

import com.stefanpetkov.medical.util.Constants;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.stefanpetkov.medical.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    PATIENT(EnumSet.of(PATIENT_READ, APPOINTMENT_READ, APPOINTMENT_WRITE)),
    DOCTOR(EnumSet.of(DOCTOR_READ, APPOINTMENT_READ, APPOINTMENT_WRITE)),
    ADMIN(EnumSet.allOf(ApplicationUserPermission.class));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority(Constants.ROLE_PREFIX + this.name()));
        return permissions;
    }

}