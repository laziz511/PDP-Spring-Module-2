package uz.pdp.online.xml_config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class City {
    public String name;
    public int numberOfPeople;
    public double size;
}
