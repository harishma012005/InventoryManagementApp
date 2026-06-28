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
                	    .requestMatchers(
                	            "/auth/forgot-password",
                	            "/auth/verify-otp",
                	            "/auth/reset-password")
                	    .permitAll()

                	    // ADMIN only create/update/delete
                	    .requestMatchers("/products/**")
                	    .hasRole("ADMIN")
                	    .requestMatchers("/reports/**")
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
                	    .requestMatchers("/dashboard/**")
                	    .hasRole("ADMIN")
                	 // ================= HOME =================

                	    .requestMatchers("/home/**")
                	    .hasAnyRole("ADMIN", "USER")
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
                	 // ================= CART =================

                	 // USER can manage own cart
                	 .requestMatchers("/cart/**")
                	 .hasRole("USER")
                	    // ADMIN delete sales
                	    .requestMatchers("/sales/delete/**")
                	    .hasRole("ADMIN")
                	 // ================= ORDERS =================

                	 // USER

                	 .requestMatchers(
                	         HttpMethod.POST,
                	         "/orders/buy-now")
                	 .hasRole("USER")

                	 .requestMatchers(
                	         HttpMethod.POST,
                	         "/orders/place-from-cart")
                	 .hasRole("USER")

                	 .requestMatchers(
                	         "/orders/my-orders")
                	 .hasRole("USER")

                	 .requestMatchers(
                	         HttpMethod.PUT,
                	         "/orders/cancel/**")
                	 .hasRole("USER")
                	 .requestMatchers(
                		        HttpMethod.PUT,
                		        "/orders/update-status/**")
                		.hasRole("ADMIN")

                		.requestMatchers(
                		        HttpMethod.GET,
                		        "/orders/filter")
                		.hasRole("ADMIN")

                	 // ADMIN

                	 .requestMatchers(
                	         "/orders/all")
                	 .hasRole("ADMIN")

                	 .requestMatchers(
                	         "/orders/get/**")
                	 .hasRole("ADMIN")

                	 .requestMatchers(
                	         HttpMethod.DELETE,
                	         "/orders/delete/**")
                	 .hasRole("ADMIN")
                	// ================= PAYMENTS =================

                	// USER can make payment
                	.requestMatchers(
                	        HttpMethod.POST,
                	        "/payments/pay")
                	.hasRole("USER")

                	// USER can view own payments
                	.requestMatchers(
                	        "/payments/my-payments")
                	.hasRole("USER")

                	// ADMIN can view all payments
                	.requestMatchers(
                	        "/payments/all")
                	.hasRole("ADMIN")

                	// ADMIN can refund payment
                	.requestMatchers(
                	        "/payments/refund/**")
                	.hasRole("ADMIN")

                	// ADMIN can delete payment
                	.requestMatchers(
                	        HttpMethod.DELETE,
                	        "/payments/**")
                	.hasRole("ADMIN")

                	// ADMIN can filter payments
                	.requestMatchers(
                	        "/payments/status/**")
                	.hasRole("ADMIN")

                	// ADMIN can view payment details
                	.requestMatchers(
                	        "/payments/order/**",
                	        "/payments/**")
                	.hasRole("ADMIN")
                	// ================= REFUND =================

                	.requestMatchers(
                	        HttpMethod.POST,
                	        "/refunds/request")
                	.hasRole("USER")

                	.requestMatchers(
                	        HttpMethod.GET,
                	        "/refunds/my-refunds")
                	.hasRole("USER")

                	.requestMatchers(
                	        HttpMethod.GET,
                	        "/refunds/*")
                	.hasAnyRole(
                	                "USER",
                	                "ADMIN")

                	.requestMatchers(
                	        HttpMethod.GET,
                	        "/refunds/all")
                	.hasRole("ADMIN")

                	.requestMatchers(
                	        HttpMethod.GET,
                	        "/refunds/status/**")
                	.hasRole("ADMIN")

                	.requestMatchers(
                	        HttpMethod.PUT,
                	        "/refunds/approve/**")
                	.hasRole("ADMIN")

                	.requestMatchers(
                	        HttpMethod.PUT,
                	        "/refunds/reject/**")
                	.hasRole("ADMIN")

                	.requestMatchers(
                	        HttpMethod.DELETE,
                	        "/refunds/**")
                	.hasRole("ADMIN")
                	// ================= PROFILE =================

                	.requestMatchers(
                	        "/profile/me")
                	.hasAnyRole(
                	        "USER",
                	        "ADMIN")

                	.requestMatchers(
                	        "/profile/update")
                	.hasAnyRole(
                	        "USER",
                	        "ADMIN")

                	.requestMatchers(
                	        "/profile/change-password")
                	.hasAnyRole(
                	        "USER",
                	        "ADMIN")
                	// ================= SUPPORT =================

                	// USER

                	.requestMatchers(
                	        HttpMethod.POST,
                	        "/support/create")
                	.hasRole("USER")

                	.requestMatchers(
                	        "/support/my-tickets",
                	        "/support/my-ticket/**")
                	.hasRole("USER")

                	// ADMIN

                	.requestMatchers(
                	        "/support/all",
                	        "/support/status/**",
                	        "/support/priority/**")
                	.hasRole("ADMIN")

                	.requestMatchers(
                	        HttpMethod.PUT,
                	        "/support/reply/**",
                	        "/support/status/**")
                	.hasRole("ADMIN")

                	.requestMatchers(
                	        HttpMethod.DELETE,
                	        "/support/**")
                	.hasRole("ADMIN")
                	.requestMatchers(
                	        HttpMethod.GET,
                	        "/orders/invoice/**")
                	.hasAnyRole("USER", "ADMIN")     
                	.requestMatchers(
                	        "/support/**")
                	.hasRole("ADMIN")
                	.anyRequest()
                	.authenticated()
                	
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