package uz.pdp.online.java_config;

import java.util.Random;

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
