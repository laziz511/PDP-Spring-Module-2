package uz.pdp.online.java_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfig {

    @Bean
    public Transform transform() {
        return new Transform();
    }

    @Bean
    public Logger logger() {
        return new Logger();
    }

}
