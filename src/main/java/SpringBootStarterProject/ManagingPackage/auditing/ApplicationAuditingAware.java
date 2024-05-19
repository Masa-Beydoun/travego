package SpringBootStarterProject.ManagingPackage.auditing;


//import com.ProjectsManagementSystem.user.User;
import SpringBootStarterProject.UserPackage.Models.BaseUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

//public class ApplicationAuditingAware implements AuditorAware<Integer> {
//
//    @Override
//    public Optional<Integer> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//        if(authentication == null ||
//        ! authentication.isAuthenticated() ||
//         authentication instanceof AnonymousAuthenticationToken){
//            return Optional.empty();
//        }
//        BaseUser userPrincipal = (BaseUser) authentication.getPrincipal();
//        return Optional.ofNullable(userPrincipal.getId());
//    }
//}
