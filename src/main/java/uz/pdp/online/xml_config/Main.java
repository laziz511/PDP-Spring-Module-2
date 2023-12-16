package uz.pdp.online.xml_config;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc-config.xml");
        Transform transform = context.getBean(Transform.class);
        transform.start();
    }
}
