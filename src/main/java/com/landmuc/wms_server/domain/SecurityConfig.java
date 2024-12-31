package com.landmuc.wms_server.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(request -> request
            .requestMatchers("/events/**")
            .hasRole("EVENT-OWNER"))
        .httpBasic(Customizer.withDefaults())
        .csrf(crsf -> crsf.disable());

    return http.build();
  }

  @Bean
  UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
    User.UserBuilder users = User.builder();

    UserDetails userA = users
        .username("userA")
        .password(passwordEncoder.encode("userAPassword123"))
        .roles("EVENT-OWNER")
        .build();

    UserDetails userB = users
        .username("userB")
        .password(passwordEncoder.encode("userBPassword344"))
        .roles("NON-OWNER")
        .build();

    UserDetails userC = users
        .username("userC")
        .password(passwordEncoder.encode("userCPassword666"))
        .roles("EVENT-OWNER")
        .build();

    return new InMemoryUserDetailsManager(userA, userB, userC);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
