package com.safeVBackend.safeVBackend.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class securityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /*
        http.csrf(customizer -> customizer.disable());

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());

        // http.formLogin(Customizer.withDefaults());
        // for web browser only return html page login form

        http.httpBasic(Customizer.withDefaults());
        // for postman

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // after stateless session creation policy after every refresh it will generate the new session id
        // in browser it will only give login form after login also ao comment out  http.formLogin(Customizer.withDefaults());

        return http.build();
         */



        return http
                .cors(Customizer.withDefaults())  // ✅ Enable CORS globally
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/register", "/login","/alert/**", "/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    // multiple users
    /*
    @Bean
    public UserDetailsService userDetailsService() {

        // 1st user
        UserDetails user1 =   User
                .withDefaultPasswordEncoder()
                .username("ADITYA")
                .password("1234")
                .roles("developer")
                .build();

        // 2nd user
        UserDetails user2 =  User
                .withDefaultPasswordEncoder()
                .username("SIDDHI")
                .password("1234")
                .roles("user")
                .build();

        return new InMemoryUserDetailsManager( user1, user2 );
    }
 */



    public securityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // deprecated NoOpPasswordEncoder
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return new ProviderManager(Collections.singletonList(authenticationProvider()));
//    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // Allow frontend requests
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}

