package nl.novi.eindopdrachtbackend.moviecollectieapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

public class MySecurityConfg {
    private final DataSource dataSource;

    public MySecurityConfg(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/info").hasRole("USER")
                .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/admins").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/users/{id}").hasRole("ADMIN")
                .requestMatchers("/authenticate").permitAll()

                .anyRequest().denyAll()

                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(jwtRequestFilter, usernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                        "SELECT username, password, enabled" +
                                " FROM users" +
                                " WHERE username=?")
                .authoritiesByUsernameQuery(
                        "SELECT username, authority" +
                                " FROM authorities " +
                                " WHERE username=?");

        return authenticationManagerBuilder.build();
    }


}
