package com.stefanpetkov.medical.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationUserDetails extends UserDetails {

    Long getUserId();
}
