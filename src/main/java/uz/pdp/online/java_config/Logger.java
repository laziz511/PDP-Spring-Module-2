package uz.pdp.online.java_config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class Logger {
    @Before("execution(* uz.pdp.online.java_config.Transform.start(..))")
    public void start() {
        System.out.println("Transform started");
        log.info("Transform started");
    }

    @After("execution(* uz.pdp.online.java_config.Transform.start(..))")
    public void end() {
        System.out.println("Transform ended");
        log.info("Transform ended");
    }

    @AfterReturning("execution(* uz.pdp.online.java_config.Transform.start(..))")
    public void success() {
        System.out.println("Transform successfully done");
        log.info("Transform successfully done");
    }


    @AfterThrowing("execution(* uz.pdp.online.java_config.Transform.start(..))")
    public void exception() {
        System.out.println("Exception occurred in Transform");
        log.info("Exception occurred in Transform");
    }
}
