package com.inventorymanagement.config;

import com.inventorymanagement.security.jwt.JwtAuthenticationFilter;
import com.inventorymanagement.security.jwt.JwtAuthenticationEntryPoint;
import com.inventorymanagement.security.jwt.CustomAccessDeniedHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity   // 🔥 enables @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    // ================= SECURITY FILTER =================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // 🔐 Exception handling
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                // 🔐 Authorization rules
                .authorizeHttpRequests(auth -> auth

                	    // PUBLIC
                	    .requestMatchers("/auth/**").permitAll()
                	    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                	    // ================= PRODUCTS =================

                	    // USER + ADMIN can view products
                	    .requestMatchers(HttpMethod.GET, "/products/**")
                	    .hasAnyRole("ADMIN", "USER")

                	    // ADMIN only create/update/delete
                	    .requestMatchers("/products/**")
                	    .hasRole("ADMIN")

                	    // ================= CATEGORY =================

                	    // USER + ADMIN can view category
                	    .requestMatchers(HttpMethod.GET, "/category/**")
                	    .hasAnyRole("ADMIN", "USER")

                	    // ADMIN only modify category
                	    .requestMatchers("/category/**")
                	    .hasRole("ADMIN")

                	    // ================= SUPPLIER =================

                	    // USER + ADMIN can view supplier
                	    .requestMatchers(HttpMethod.GET, "/supplier/**")
                	    .hasAnyRole("ADMIN", "USER")

                	    // ADMIN only modify supplier
                	    .requestMatchers("/supplier/**")
                	    .hasRole("ADMIN")

                	    // ================= PURCHASE =================

                	    .requestMatchers("/purchases/**")
                	    .hasRole("ADMIN")

                	    // ================= STOCK =================

                	    .requestMatchers("/stock/**")
                	    .hasRole("ADMIN")

                	    // ================= USERS =================

                	    .requestMatchers("/users/**")
                	    .hasRole("ADMIN")

                	    // ================= SALES =================

                	    // USER create sale
                	    .requestMatchers(HttpMethod.POST, "/sales/create")
                	    .hasRole("USER")

                	    // USER see own sales
                	    .requestMatchers("/sales/my-sales")
                	    .hasRole("USER")

                	    // ADMIN see all sales
                	    .requestMatchers("/sales/all")
                	    .hasRole("ADMIN")

                	    // ADMIN see sale by id
                	    .requestMatchers("/sales/get/**")
                	    .hasRole("ADMIN")

                	    // ADMIN delete sales
                	    .requestMatchers("/sales/delete/**")
                	    .hasRole("ADMIN")

                	    .anyRequest().authenticated()
                	)
                // 🔐 Stateless session (VERY IMPORTANT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 🔐 JWT filter (FIX FOR YOUR ERROR)
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // ================= AUTH MANAGER (FIX YOUR ERROR) =================
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ================= PASSWORD ENCODER =================
   
}