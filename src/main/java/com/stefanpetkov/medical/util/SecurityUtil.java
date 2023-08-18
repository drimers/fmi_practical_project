package com.stefanpetkov.medical.util;

import com.stefanpetkov.medical.security.ApplicationUserRole;
import com.stefanpetkov.medical.security.authentication.ApplicationUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Utility singleton class to assist global access to the ApplicationUserDetailsImpl class fields
 * @see com.stefanpetkov.medical.security.authentication.ApplicationUserDetailsImpl
 */
@Slf4j
public final class SecurityUtil {


    private SecurityUtil() {
    }

    public static SecurityUtil getInstance() {
        return SecurityUtilHelper.INSTANCE;
    }

    private static class SecurityUtilHelper {
        private static final SecurityUtil INSTANCE = new SecurityUtil();
    }


    //== public methods ==
    public Long getLoggedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.info("Current user not logged in");
            return null;
        }
        ApplicationUserDetails applicationUserDetails = (ApplicationUserDetails) authentication.getPrincipal();
        log.info("Retrieved user details ={}", applicationUserDetails);
        Long loggedUserId = applicationUserDetails.getUserId();
        log.info("Retrieved user ID={}", loggedUserId);
        return loggedUserId;
    }

    public Long getLoggedUserId(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.info("Current user not logged in");
            return null;
        }
        ApplicationUserDetails applicationUserDetails = (ApplicationUserDetails) authentication.getPrincipal();
        log.info("Retrieved user details ={}", applicationUserDetails);
        Long loggedUserId = applicationUserDetails.getUserId();
        log.info("Retrieved user ID={}", loggedUserId);
        return loggedUserId;
    }

    public ApplicationUserRole getLoggedUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.info("Current user not logged in");
            return null;
        }
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        ApplicationUserRole applicationUserRole = grantedAuthorities.stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().startsWith(Constants.ROLE_PREFIX))
                .map(grantedAuthority -> grantedAuthority.getAuthority().substring(Constants.ROLE_PREFIX.length()))
                .map(ApplicationUserRole::valueOf)
                .findFirst()
                .get();
        log.info("Current user role={}", applicationUserRole);
        return applicationUserRole;
    }

    public ApplicationUserRole getLoggedUserRole(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.info("Current user not logged in");
            return null;
        }
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        ApplicationUserRole applicationUserRole = grantedAuthorities.stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().startsWith(Constants.ROLE_PREFIX))
                .map(grantedAuthority -> grantedAuthority.getAuthority().substring(Constants.ROLE_PREFIX.length()))
                .map(ApplicationUserRole::valueOf)
                .findFirst()
                .get();
        log.info("Current user role={}", applicationUserRole);
        return applicationUserRole;
    }

}
