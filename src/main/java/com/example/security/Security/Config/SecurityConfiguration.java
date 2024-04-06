package com.example.security.Security.Config;

import com.example.security.Models.Author;
import com.example.security.RolesAndPermission.Roles;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.example.security.RolesAndPermission.Permissions.AUTHOR_CREATE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->
                      req

                        //        .requestMatchers("/Author/**").hasRole(Roles.AUTHOR.name())
                               //.requestMatchers("/Resources/stream").authenticated()
                               // .requestMatchers("/Student/**").hasRole(Roles.SIMPLE_STUDENT.name())
                               // .requestMatchers("/Course/**").hasAnyRole(Roles.SIMPLE_STUDENT.name(),Roles.AUTHOR.name())
                               // .requestMatchers("/Lecture/**").hasAnyRole(Roles.SIMPLE_STUDENT.name(),Roles.AUTHOR.name())

                                .anyRequest().permitAll()

                )
               // .formLogin(login->login.failureHandler(customLoginFailureHandler).loginPage("/BaseUser/Login").permitAll())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
              //.addFilterBefore(LoginAttemptFilter(),UsernamePasswordAuthenticationFilter.class)
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(logout->
                        logout
                                .logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

return http.build();
    }



}
