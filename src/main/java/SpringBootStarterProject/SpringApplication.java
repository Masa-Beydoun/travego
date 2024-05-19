package SpringBootStarterProject;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);


	}

}

