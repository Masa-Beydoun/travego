package SpringBootStarterProject.ManagingPackage.Security.Config;

import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Models.Manager;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.UserPackage.Repositories.ManagerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {


    private final ClientRepository clientRepository;

    private final ManagerRepository managerRepository;




    private final HttpServletRequest request;

  //  @Bean
   // public Environment environment() {
      //  return new StandardEnvironment();
   // }

//    @Bean
//    public UserDetailsService userService() {
//        return username -> {
//            UserDetailsService userDetails = null;
//
//            // Attempt to find the user in the client repository
//            Optional<Client> clientOptional = clientRepository.findByEmail(username);
//            if (clientOptional.isPresent()) {
//                userDetails = loadUserByUsername(clientOptional.get());
//            }
//
//            // If not found in the client repository, attempt to find in the manager repository
//            if (userDetails == null) {
//                Optional<Manager> managerOptional = managerRepository.findByEmail(username);
//                if (managerOptional.isPresent()) {
//                    userDetails = loadUserByUsername(managerOptional.get());
//                }
//            }
//
//            // Throw exception if user is still not found
//            if (userDetails == null) {
//                throw new UsernameNotFoundException("User not found with email: " + username);
//            }
//
//            return userDetails;
//        };
//    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username ->
//              managerRepository.findByEmail(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
//
//
//    };
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<Client> client = clientRepository.findByEmail(username);
            if (client.isPresent()) {
                Client foundClient = client.get();
                return new User(
                        foundClient.getEmail(),
                        foundClient.getPassword(),
                        new ArrayList<>() // Add authorities if necessary
                );
            }

            Optional<Manager> manager = managerRepository.findByEmail(username);
            if (manager.isPresent()) {
                Manager foundManager = manager.get();
                return new User(
                        foundManager.getEmail(),
                        foundManager.getPassword(),
                        new ArrayList<>() // Add authorities if necessary
                );
            }
            throw new UsernameNotFoundException("User not found with email: " + username);
        };
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
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
