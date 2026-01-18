package nl.novi.eindopdrachtbackend.moviecollectieapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.*;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${spring.security.oauth2.resourceserver.jwt.audiences}")
    private String audience;

    @Value("${client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .httpBasic(h -> h.disable())
                .csrf(c -> c.disable())
                .cors(c -> {})
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/movies/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/genres/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/movies/*/poster").hasAnyRole("USER","ADMIN")

                        .requestMatchers(HttpMethod.POST, "/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movies/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/genres/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/movies/*/poster").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movies/*/poster").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder = JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new JwtAudienceValidator(audience);
        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withIssuer, withAudience);

        decoder.setJwtValidator(validator);
        return decoder;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new Converter<>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt source) {
                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (String authority : getAuthorities(source)) {
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + authority));
                }
                return grantedAuthorities;
            }
            private List<String> getAuthorities(Jwt jwt){
                Map<String, Object> resourceAcces = jwt.getClaim("resource_access");
                if(resourceAcces != null){
                    if( resourceAcces.get(clientId) instanceof Map) {
                        Map<String, Object> client = (Map<String, Object>) resourceAcces.get(clientId);
                        if(client != null && client.containsKey("roles")) {
                            return (List<String>) client.get("roles");
                        }
                    }
                }
                return new ArrayList<>();
            }
        });
        return jwtAuthenticationConverter;
    }
}