package uz.pdp.online.java_config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CityConfig.class);
        City cityBean = context.getBean(City.class);

        System.out.println(cityBean);

        context.close();
    }
}
