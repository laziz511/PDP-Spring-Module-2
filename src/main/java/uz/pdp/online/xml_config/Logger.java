package uz.pdp.online.xml_config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Logger {

    public void start() {
        System.out.println("Transform started");
        log.info("Transform started");
    }

    public void end() {
        System.out.println("Transform ended");
        log.info("Transform ended");
    }

    public void success() {
        System.out.println("Transform successfully done");
        log.info("Transform successfully done");
    }

    public void exception() {
        System.out.println("Exception occurred in Transform");
        log.info("Exception occurred in Transform");
    }
}
