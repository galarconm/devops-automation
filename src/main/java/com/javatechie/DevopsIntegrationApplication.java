package com.javatechie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main Spring Boot application class for DevOps integration demo.
 */
@SpringBootApplication
@RestController
public class DevopsIntegrationApplication {

    /**
     * Returns a welcome message.
     * @return welcome message string
     */
    @GetMapping
    public String message() {
        return "welcome to javatechie";
    }

    /**
     * Main method to start the Spring Boot application.
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(DevopsIntegrationApplication.class, args);
    }

}
