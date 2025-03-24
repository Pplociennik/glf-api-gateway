package com.goaleaf.gateway.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * TODO: Describe this class.
 *
 * @author Created by: Pplociennik at 18.02.2025 19:29
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private Environment environment;

    @Autowired
    public SecurityConfig( Environment environment ) {
        this.environment = environment;
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        String jwkUri = environment.getProperty( "spring.security.oauth2.resourceserver.jwt.jwk-set-uri" );
//        return NimbusJwtDecoder.withJwkSetUri( jwkUri ).build();
//    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http ) {
        http
                .authorizeExchange( exchanges -> exchanges
                        .pathMatchers( HttpMethod.GET )
                        .permitAll()
                        .pathMatchers( "/glf-accounts/**" )
                        .authenticated()
                        .pathMatchers( "/glf-communities/**" )
                        .authenticated() )
                .oauth2ResourceServer( oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(
                        aJwtSpec -> aJwtSpec.jwtAuthenticationConverter( grantedAuthoritiesExtractor() ) ) );

        http.csrf( ServerHttpSecurity.CsrfSpec::disable );

        return http.build();

    }

    Converter< Jwt, Mono< AbstractAuthenticationToken > > grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter( new KeycloakRoleConverter() );

        return new ReactiveJwtAuthenticationConverterAdapter( jwtAuthenticationConverter );
    }

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        // Custom implementation if needed
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//
//        return jwtAuthenticationConverter;
//    }

}
