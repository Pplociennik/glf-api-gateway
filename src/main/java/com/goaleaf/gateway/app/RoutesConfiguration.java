package com.goaleaf.gateway.app;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

import static com.goaleaf.gateway.app.RoutesConfigurationDetails.DEFAULT_CIRCUIT_BREAKER_CONFIG;

/**
 * Configuration class for defining custom routes for the Goaleaf application.
 * This class uses Spring Cloud Gateway to route requests to different services.
 * <p>
 * Routes defined:
 * - /goaleaf/accounts/** -> lb://ACCOUNTS
 * - /goaleaf/communities/** -> lb://COMMUNITIES
 * <p>
 * The routes use load balancing to distribute requests across instances of the services.
 * <p>
 * Author: Pplociennik
 * Date: 26.08.2024
 */
@Configuration
class RoutesConfiguration {

    /**
     * Configuration for the Accounts service route.
     * Routes requests from /glf-accounts/** to the GLF-ACCOUNTS service.
     * Applies a rewrite path filter and a default circuit breaker configuration.
     */
    private static final Function< PredicateSpec, Buildable< Route > > ACCOUNTS_SERVICE_ROUTE_CONFIGURATION =
            path -> path.path( RoutesConfigurationDetails.Accounts.ACCOUNTS_SERVICE_PATH )
                    .filters(
                            filter -> filter
                                    .rewritePath( RoutesConfigurationDetails.Accounts.ACCOUNTS_SERVICE_REWRITE_REGEX, RoutesConfigurationDetails.Accounts.ACCOUNTS_SERVICE_REWRITE_REPLACEMENT )
                                    .circuitBreaker( DEFAULT_CIRCUIT_BREAKER_CONFIG ) )
                    .uri( "lb://GLF-ACCOUNTS" );

    /**
     * Configuration for the Communities service route.
     * Routes requests from /glf-communities/** to the GLF-COMMUNITIES service.
     * Applies a rewrite path filter and a default circuit breaker configuration.
     */
    private static final Function< PredicateSpec, Buildable< Route > > COMMUNITIES_SERVICE_ROUTE_CONFIGURATION =
            path -> path.path( RoutesConfigurationDetails.Communities.COMMUNITIES_SERVICE_PATH )
                    .filters(
                            filter -> filter
                                    .rewritePath( RoutesConfigurationDetails.Communities.COMMUNITIES_SERVICE_REWRITE_REGEX, RoutesConfigurationDetails.Communities.COMMUNITIES_SERVICE_REWRITE_REPLACEMENT )
                                    .circuitBreaker( DEFAULT_CIRCUIT_BREAKER_CONFIG ) )
                    .uri( "lb://GLF-COMMUNITIES" );

    /**
     * Defines custom routes for the Goaleaf application.
     *
     * @param builder
     *         the RouteLocatorBuilder used to build the routes
     * @return a RouteLocator containing the defined routes
     */
    @Bean
    RouteLocator goaleafRoutesLocator( RouteLocatorBuilder builder ) {
        return builder.routes()
                .route( ACCOUNTS_SERVICE_ROUTE_CONFIGURATION )
                .route( COMMUNITIES_SERVICE_ROUTE_CONFIGURATION )
                .build();
    }
}