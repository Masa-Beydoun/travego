package SpringBootStarterProject.ManagingPackage.Utils;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.exception.UnAuthorizedException;
import SpringBootStarterProject.UserPackage.Models.BaseUser;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.UserPackage.Repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UtilsService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ManagerRepository managerRepository;

    public BaseUser extractCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UnAuthorizedException("Authentication error");
        }
        var client = clientRepository.findByEmail(authentication.getName()).orElse(null);
        if(client == null){
            return managerRepository.findByEmail(authentication.getName()).orElse(null);
        }
        return client;
    }

}
