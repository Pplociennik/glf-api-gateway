package com.goaleaf.gateway.app;

import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.util.function.Consumer;

/**
 * Utility class containing configuration details for routes and circuit breakers in the Goaleaf application.
 * This class provides default configurations and specific configurations for different services.
 * <p>
 * Author: Pplociennik
 * Date: 17.09.2024
 */
class RoutesConfigurationDetails {

//    ####################################################################################################################
//    Consumers for circuit breaker configurations
//    ####################################################################################################################

    /**
     * Default circuit breaker configuration.
     * Sets the name to "glfBreaker" and the fallback URI to "forward:/fallback/contactSupport".
     */
    static final Consumer< SpringCloudCircuitBreakerFilterFactory.Config > DEFAULT_CIRCUIT_BREAKER_CONFIG = config -> config
            .setName( "glfBreaker" )
            .setFallbackUri( "forward://fallback/contactSupport" );


    /**
     * Circuit breaker configuration for the Accounts service.
     * Sets the name to "accountsBreaker" and the fallback URI to "forward:/fallback/contactSupport".
     */
    static final Consumer< SpringCloudCircuitBreakerFilterFactory.Config > ACCOUNTS_CIRCUIT_BREAKER_CONFIG = config -> config
            .setName( "accountsBreaker" )
            .setFallbackUri( "forward:/fallback/contactSupport" );

    /**
     * Circuit breaker configuration for the Communities service.
     * Sets the name to "communitiesBreaker" and the fallback URI to "forward:/fallback/contactSupport".
     */
    static final Consumer< SpringCloudCircuitBreakerFilterFactory.Config > COMMUNITIES_CIRCUIT_BREAKER_CONFIG = config -> config
            .setName( "communitiesBreaker" )
            .setFallbackUri( "forward:/fallback/contactSupport" );

//    ####################################################################################################################
//    Consumers for retry pattern configurations
//    ####################################################################################################################

    /**
     * Default retry configuration.
     */
    static final Consumer< RetryGatewayFilterFactory.RetryConfig > DEFAULT_RETRY_CONFIG = config -> config
            .setRetries( 3 )
            .setMethods( HttpMethod.GET )
            .setBackoff( Duration.ofMillis( 100 ), Duration.ofMillis( 1000 ), 2, true );

//    ####################################################################################################################
//    Constants for service paths and rewrite configurations
//    ####################################################################################################################

    static class Accounts {
        /**
         * Path for the Accounts service.
         */
        static final String ACCOUNTS_SERVICE_PATH = "/glf-accounts/**";

        /**
         * Regular expression for rewriting the Accounts service path.
         */
        static final String ACCOUNTS_SERVICE_REWRITE_REGEX = "/glf-accounts/?(?<remaining>.*)";

        /**
         * Replacement string for the Accounts service path rewrite.
         */
        static final String ACCOUNTS_SERVICE_REWRITE_REPLACEMENT = "/${remaining}";
    }

    static class Communities {
        /**
         * Path for the Communities service.
         */
        static final String COMMUNITIES_SERVICE_PATH = "/glf-communities/**";

        /**
         * Regular expression for rewriting the Communities service path.
         */
        static final String COMMUNITIES_SERVICE_REWRITE_REGEX = "/glf-communities/?(?<remaining>.*)";

        /**
         * Replacement string for the Communities service path rewrite.
         */
        static final String COMMUNITIES_SERVICE_REWRITE_REPLACEMENT = "/${remaining}";
    }
}
