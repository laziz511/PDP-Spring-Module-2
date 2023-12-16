package uz.pdp.online.xml_config;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("ioc-config.xml");
        City cityBeanFromXml = xmlApplicationContext.getBean(City.class);

        System.out.println(cityBeanFromXml);

        xmlApplicationContext.close();
    }
}
