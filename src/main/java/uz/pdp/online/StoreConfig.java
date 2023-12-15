package uz.pdp.online;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreConfig {

    @Bean
    public Item item() {
        return new Item();
    }

    @Bean(name = "storeConstructor")
    public Store storeConstructor(Item item) {
        return new Store(item);
    }

    @Bean(name = "storeSetter")
    public Store storeSetter(Item item) {
        Store store = new Store();
        store.setItem(item);
        return store;
    }
}
