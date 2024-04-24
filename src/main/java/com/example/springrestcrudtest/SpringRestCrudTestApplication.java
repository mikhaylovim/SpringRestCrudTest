package com.example.springrestcrudtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class SpringRestCrudTestApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringRestCrudTestApplication.class, args);
        String[] beanNames = context.getBeanDefinitionNames();
        log.debug("_registered beans: {}", Arrays.toString(beanNames));
        log.info("_registered bean count: {}", beanNames.length);
    }
}
