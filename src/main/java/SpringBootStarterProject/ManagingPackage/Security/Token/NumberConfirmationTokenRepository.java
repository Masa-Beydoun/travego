package SpringBootStarterProject.ManagingPackage.Security.Token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NumberConfirmationTokenRepository extends JpaRepository<NumberConfirmationToken,Integer> {
    Optional< NumberConfirmationToken> findByCode(String codeNumber);



   Optional<NumberConfirmationToken >deleteNumberConfirmationTokenByClient_email(String email);

   Optional<NumberConfirmationToken >getNumberConfirmationTokenByClient_email(String email);
}
