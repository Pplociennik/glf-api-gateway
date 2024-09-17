package com.goaleaf.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller for handling fallback routes in the Goaleaf application.
 * <p>
 * This controller provides endpoints that are used as fallback mechanisms
 * when a service is unavailable or an error occurs. The fallback routes
 * return predefined responses to the client.
 * <p>
 * Author: Pplociennik
 * Date: 17.09.2024
 */
@RestController
@RequestMapping( path = "/fallback" )
public class DefaultFallbackController {

    @RequestMapping( "/contactSupport" )
    Mono< String > contactSupport() {
        return Mono.just( "An error occurred. Please try again later or contact the support team." );
    }
}
