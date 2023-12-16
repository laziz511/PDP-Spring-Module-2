package uz.pdp.online.java_config;

import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:city.properties")
public class CityConfig {

    @Bean
    @Scope("prototype")
    public City city() {
        return new City();
    }
}
