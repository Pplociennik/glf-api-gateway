package com.goaleaf.gateway.app.config;

import com.github.pplociennik.commons.service.SystemPropertiesReaderService;
import com.github.pplociennik.commons.service.config.CommonBeansConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * TODO: Describe this class.
 *
 * @author Created by: Pplociennik at 18.02.2025 19:29
 */
@Configuration
@EnableWebFluxSecurity
@Import( value = CommonBeansConfig.class )
public class SecurityConfig {

    private Environment environment;

    private SystemPropertiesReaderService propertyService;

    @Autowired
    public SecurityConfig( Environment environment, SystemPropertiesReaderService propertyService ) {
        this.environment = environment;
        this.propertyService = propertyService;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http ) {
        http
                .cors( customizer -> customizer.configurationSource( corsConfigurationSource() ) )
                .authorizeExchange( exchanges -> exchanges
                        .pathMatchers( "/actuator/**", "/actuator/health/**" ).permitAll()
                        .pathMatchers( "/glf-accounts/**" )
                        .hasRole( "ACCOUNTS" )
                        .pathMatchers( "/glf-communities/**" )
                        .hasRole( "COMMUNITIES" ) )
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        String clientUrlProperty = propertyService.readProperty( "com.goaleaf.accounts.clientUri" );
        String clientUrl = clientUrlProperty != null && !clientUrlProperty.isBlank() ? clientUrlProperty : "";

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins( List.of( clientUrl ) );
        config.setAllowedMethods( List.of( "GET", "POST", "PUT", "DELETE", "OPTIONS" ) );
        config.setAllowedHeaders( List.of( "*" ) );
        config.setAllowCredentials( true );
        config.setMaxAge( 3600L );

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", config );
        return source;
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