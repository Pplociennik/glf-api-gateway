package com.goaleaf.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * TODO: Describe this class.
 *
 * @author Created by: Pplociennik at 17.09.2024 17:41
 */
@RestController
@RequestMapping( path = "/fallback" )
public class DefaultFallbackController {

    @RequestMapping( "/contactSupport" )
    Mono< String > contactSupport() {
        return Mono.just( "An error occurred. Please contact the support team." );
    }
}
