package com.example.security.Security.Config;

import com.backblaze.b2.client.B2Sdk;
import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.example.security.Models.BaseUser;
import com.example.security.Repository.AuthorRepository;
import com.example.security.Repository.BaseUserRepository;
import com.example.security.Security.Token.ConfirmationRepository;
import com.example.security.Security.Token.ConfirmationToken;
import com.example.security.Validator.ObjectsValidator;
import com.example.security.Validator.PasswordConstraintValidator;
import com.example.security.auditing.ApplicationAuditAware;
import com.example.security.configuration.BackblazeConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AuthorRepository authorRepository;
    private final BaseUserRepository baseUserRepository;
    private final ConfirmationRepository confirmationRepository;

    @Bean
    public UserDetailsService userDetailsService() {

        return username ->
                baseUserRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("SSSSSSSSS"));

    }

    private final BackblazeConfiguration backblazeConfiguration;


@Bean
public B2StorageClient b2StorageClient()
{
    return B2StorageClientFactory.createDefaultFactory().create(
             backblazeConfiguration.getAccountId()
            ,backblazeConfiguration.getApplicationKeyId()
            ,backblazeConfiguration.getApplicationKey());
}

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
   @Bean
    public AuditorAware<Integer> auditorAware()
    {
        return new ApplicationAuditAware();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    //TRY ADDING SALT BY MYSELF
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
