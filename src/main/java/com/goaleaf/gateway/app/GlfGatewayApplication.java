package com.goaleaf.gateway.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Goaleaf Gateway application.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication
public class GlfGatewayApplication {

    /**
     * The entry point of the Goaleaf Gateway application.
     *
     * @param args
     *         command-line arguments passed to the application
     */
    public static void main( String[] args ) {
        SpringApplication.run( GlfGatewayApplication.class, args );
    }

}