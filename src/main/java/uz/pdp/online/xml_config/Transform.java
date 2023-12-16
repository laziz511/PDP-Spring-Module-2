package uz.pdp.online.xml_config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Random;

@Aspect
@Component
public class Transform {
    private static final Random random = new Random();
    public void start() {
        if (random.nextBoolean()) {
            for (int i = 0; i < 10; i++) {
                System.out.println("number: " + i);
            }
        } else {
            throw new RuntimeException("Transform exception");
        }
    }
}
