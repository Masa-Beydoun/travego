package SpringBootStarterProject.ManagingPackage.Testing;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class aspectClass {
    private static final Logger logger = LoggerFactory.getLogger(aspectClass.class);
    @AfterThrowing("execution(public void samer())")
    public void aspect (JoinPoint joinPoint)
    {
        logger.error("Before executing method: {}", joinPoint.getSignature().getName());
    }
}
