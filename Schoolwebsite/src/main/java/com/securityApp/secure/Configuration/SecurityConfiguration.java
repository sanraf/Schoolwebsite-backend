package com.securityApp.secure.Configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   return http
                .csrf()
                .disable()
               .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                   @Override
                   public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                       CorsConfiguration config = new CorsConfiguration();
                       config.setAllowedOrigins(Collections.singletonList("http://localhost:3000/"));
                       config.setAllowedMethods(Collections.singletonList("*"));
                       config.setAllowedHeaders(Collections.singletonList("*"));
                       config.setMaxAge(3600L);
                       return  config;
                   }
               }) )
                .authorizeHttpRequests(auth-> auth
                . requestMatchers(HttpMethod.POST,"user/registration","/authenticate/*").permitAll()
                .requestMatchers(HttpMethod.GET, "user/findByUsername/{userName}").permitAll()



                .requestMatchers("user/updateUser/{username}","/user/pagination/{offset}/{pageSize}")
                .hasAnyRole("USER","ADMIN")
                .requestMatchers("user/deleteUser/{userName}")
                .hasAnyRole("ADMIN")

                ).sessionManagement(session->
                   session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .formLogin(Customizer.withDefaults())
               .httpBasic(Customizer.withDefaults())
               .build();

    }

}
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYW50b3MiLCJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTcwNjY0MzAxNCwiZXhwIjoxNzA2NjQ2NjE0fQ.WAfqWkOLDb8MmXnM7sm27GAKN-2_C74oPTLIbv7wHLs

//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsInJvbGUiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sImlhdCI6MTcwNjY0MzAzMywiZXhwIjoxNzA2NjQ2NjMzfQ.8dLyayGDqqYlt3owSTlw9vuilk9oPQvtO9U3EkISmfo