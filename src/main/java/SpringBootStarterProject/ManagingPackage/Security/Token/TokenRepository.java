package SpringBootStarterProject.ManagingPackage.Security.Token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token,Integer> {


   List<Token> findTokensByClientId(Integer id);

    Optional<Token> findByToken(String token);

}
