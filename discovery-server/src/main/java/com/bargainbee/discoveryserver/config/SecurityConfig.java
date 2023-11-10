//package com.bargainbee.discoveryserver.config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Value(value = "${eureka.username}")
//    private String username;
//
//    @Value("${eureka.password}")
//    private String password;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> {
//                    auth.anyRequest().authenticated();
//                });
//
//        http.httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username(username)
//                .password("{noop}" + password)
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin);
//    }
//}
