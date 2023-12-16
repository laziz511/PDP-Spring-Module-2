package uz.pdp.online.java_config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class City {

    @Value("${city.name:'Tashkent'}")
    private String name;

    @Value("${city.numberOfPeople:3000000}")
    private int numberOfPeople;

    @Value("${city.size:270000D}")
    private double size;
}
