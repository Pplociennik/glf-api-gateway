package com.goaleaf.gateway.app;

import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory;

import java.util.function.Consumer;

/**
 * Utility class containing configuration details for routes and circuit breakers in the Goaleaf application.
 * This class provides default configurations and specific configurations for different services.
 * <p>
 * Author: Pplociennik
 * Date: 17.09.2024
 */
class RoutesConfigurationDetails {

    /**
     * Default circuit breaker configuration.
     * Sets the name to "glfBreaker" and the fallback URI to "forward:/fallback/contactSupport".
     */
    static final Consumer< SpringCloudCircuitBreakerFilterFactory.Config > DEFAULT_CIRCUIT_BREAKER_CONFIG = config -> config
            .setName( "glfBreaker" )
            .setFallbackUri( "forward:/fallback/contactSupport" );

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
