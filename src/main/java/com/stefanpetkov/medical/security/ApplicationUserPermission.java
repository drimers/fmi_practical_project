package com.stefanpetkov.medical.security;

public enum ApplicationUserPermission {

    PATIENT_READ("patient:read"),
    PATIENT_WRITE("patient:write"),
    DOCTOR_READ("doctor:read"),
    DOCTOR_WRITE("doctor:write"),
    APPOINTMENT_READ("appointment:read"),
    APPOINTMENT_WRITE("appointment:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");


    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }


    public String getPermission() {
        return permission;
    }

}