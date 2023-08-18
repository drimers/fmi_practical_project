package com.stefanpetkov.medical.security.authentication;

import com.stefanpetkov.medical.domain.UserCredentials;
import com.stefanpetkov.medical.repositories.CredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final CredentialsRepository credentialsRepository;

    @Autowired
    public ApplicationUserDetailsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }


    @Override
    public ApplicationUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserCredentials credentials = credentialsRepository.findByEmail(email).orElseThrow(() -> {
            String errorMessage = "User with email={" + email + "} not found.";
            log.error(errorMessage);
            return new UsernameNotFoundException(errorMessage);
        });


        ApplicationUserDetails userDetails = new ApplicationUserDetailsImpl(credentials);
        log.info("Successfully extracted user details for user with email={}, applicationUserDetails={}", email, userDetails);

        return userDetails;
    }
}
