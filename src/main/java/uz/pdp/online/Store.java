package uz.pdp.online;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Store {
    private Item item;

    @Autowired
    public Store(Item item) {
        this.item = item;
    }

    public Store() {
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

