package SpringBootStarterProject;


import SpringBootStarterProject.UserPackage.Models.MoneyCode;
import SpringBootStarterProject.UserPackage.Repositories.MoneyCodeRepository;
import com.pusher.client.channel.impl.PrivateChannelImpl;
import com.pusher.client.channel.impl.message.PresenceMemberData;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.impl.InternalConnection;
import com.pusher.rest.Pusher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.pusher.client.channel.*;

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
//
//    @Value("${pusher.key}")
//    private static String pusherKey;
//    @Value("${pusher.secret}")
//    private static String pusherSecret;


//	@Autowired
//	private  fileService;


    public static void main(String[] args) {

        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);

//        System.out.println(pusherKey);
//            Pusher pusher = new Pusher("1850636",  "badcf5d16e9c5f14a22f", "3e6d390cbe18819d52ad");
//        pusher.setCluster("ap2");
//        pusher.setEncrypted(true);
//        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "Welcome to Travego Platform"));
    }

    @Override
    public void run(String... args) throws Exception {
//		fileService.init();

           moneyCodeRepository.deleteAll();
        if (moneyCodeRepository.findMoneyCodeByCode("377257606851829") == null) {
            MoneyCode moneyCode = MoneyCode.builder()
                    .code("377257606851829")
                    .balance(4000)
                    .valid(true)
                    .build();
            moneyCodeRepository.save(moneyCode);
        }
        if (moneyCodeRepository.findMoneyCodeByCode("484314274932020") == null) {
            MoneyCode moneyCode2 = MoneyCode.builder()
                    .code("484314274932020")
                    .balance(5000)
                    .valid(true)
                    .build();
            moneyCodeRepository.save(moneyCode2);
        }


        if (moneyCodeRepository.findMoneyCodeByCode("481766441208295") == null) {
            MoneyCode moneyCode3 = MoneyCode.builder()
                    .code("481766441208295")
                    .balance(5000)
                    .valid(true)
                    .build();
            moneyCodeRepository.save(moneyCode3);
        }


    }

}

