package SpringBootStarterProject;


import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.ResourcesPackage.FileService;
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

public class SpringApplication implements CommandLineRunner {

	@Autowired
	private FileService fileService;

//	@Autowired
//	private  fileService;



	public static void main(String[] args) {


		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileService.init();
	}

}

