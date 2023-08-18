package com.stefanpetkov.medical.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.stefanpetkov.medical.util.Constants.*;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity // -> should work the same as @EnableGlobalMethodSecurity which is deprecated
public class SecurityConfiguration {


    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()))//h2-console visibility config -> https://github.com/spring-projects/spring-security/issues/12546
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(toH2Console()).permitAll())// end of h2-console visibility config
                .authorizeHttpRequests().requestMatchers(ALLOWED_REQUEST_MATCHERS).permitAll()
//                .requestMatchers(Constants.REQUEST_MAPPING_DOCTOR).hasAnyRole(ApplicationUserRole.DOCTOR.name(), ApplicationUserRole.ADMIN.name())
//                .requestMatchers(Constants.REQUEST_MAPPING_PATIENT).hasAnyRole(ApplicationUserRole.PATIENT.name(), ApplicationUserRole.ADMIN.name())
//                .requestMatchers(Constants.REQUEST_MAPPING_ADMIN).hasRole(ApplicationUserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(REQUEST_MAPPING_LOGIN)
                .loginProcessingUrl(LOGIN_FORM_ACTION)
                .defaultSuccessUrl(REQUEST_MAPPING_HOME+"?login", Boolean.TRUE)
                .usernameParameter(LOGIN_FORM_USERNAME_PARAMETER)
                .passwordParameter(LOGIN_FORM_PASSWORD_PARAMETER)
                .and()
                .logout()
                .logoutUrl(LOGOUT_ACTION)
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ACTION, HTTP_METHOD_GET))
                .clearAuthentication(Boolean.TRUE)
                .invalidateHttpSession(Boolean.TRUE)
                .deleteCookies(SESSION_COOKIES_TO_DELETE)
                .logoutSuccessUrl(REQUEST_MAPPING_LOGIN+"?logout");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }


    // TODO - Note this is covered in the permitAll especially for the in memory database
    // Here is only for reference and how the new way is done in Spring Security 6 and Spring boot 3
    // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
    //
    //@Bean
    //public WebSecurityCustomizer webSecurityCustomizer() {
    //     return (web) -> web.ignoring().requestMatchers("/h2-console/**");
    //}

}
