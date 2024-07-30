package SpringBootStarterProject;


import SpringBootStarterProject.UserPackage.Models.MoneyCode;
import SpringBootStarterProject.UserPackage.Repositories.MoneyCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringApplication implements CommandLineRunner {


    private final MoneyCodeRepository moneyCodeRepository;

//	@Autowired
//	private  fileService;


    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//		fileService.init();
        if (moneyCodeRepository.findMoneyCodeByCode("377257606851829") != null) {
            MoneyCode moneyCode = MoneyCode.builder()
                    .code("377257606851829")
                    .balance(400)
                    .valid(true)
                    .build();
            moneyCodeRepository.save(moneyCode);
        }
        if (moneyCodeRepository.findMoneyCodeByCode("484314274932020") != null) {
            MoneyCode moneyCode2 = MoneyCode.builder()
                    .code("484314274932020")
                    .balance(500)
                    .valid(true)
                    .build();
            moneyCodeRepository.save(moneyCode2);
        }


        if (moneyCodeRepository.findMoneyCodeByCode("481766441208295") != null) {
            MoneyCode moneyCode3 = MoneyCode.builder()
                    .code("481766441208295")
                    .balance(500)
                    .valid(true)
                    .build();
            moneyCodeRepository.save(moneyCode3);
        }


    }

}

