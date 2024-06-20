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

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@RequiredArgsConstructor
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
		MoneyCode moneyCode= MoneyCode.builder()
				.code("samer")
				.balance(100)
				.valid(true)
				.build();
		MoneyCode moneyCode2= MoneyCode.builder()
				.code("ali")
				.balance(500)
				.valid(true)
				.build();

		moneyCodeRepository.save(moneyCode);
		moneyCodeRepository.save(moneyCode2);

	}

}

