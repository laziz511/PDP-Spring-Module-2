package uz.pdp.online.java_config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Transform transform = context.getBean(Transform.class);
        transform.start();

    }
}
