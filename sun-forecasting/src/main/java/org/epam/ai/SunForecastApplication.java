package org.epam.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.epam.ai.*")
@SpringBootApplication
public class SunForecastApplication {
    public static void main(String[] args) {
        SpringApplication.run(SunForecastApplication.class, args);
    }
}

