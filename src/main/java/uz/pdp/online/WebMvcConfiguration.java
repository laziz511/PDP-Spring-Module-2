package uz.pdp.online;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("uz.pdp.online")
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    public WebMvcConfiguration(ApplicationContext applicationContext) {
    }
}