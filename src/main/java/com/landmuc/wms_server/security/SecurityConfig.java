package com.landmuc.wms_server.security;

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
            .requestMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(Customizer.withDefaults())
        // .httpBasic(Customizer.withDefaults()) // for basic request for APIs etc
        .csrf(crsf -> crsf.disable())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // solves "localhost refusted
                                                                                           // to connect" in h2-console

    return http.build();
  }

  // @Bean // FilterChain gets called prior to the controller
  // SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  // http
  // .authorizeHttpRequests(request -> request
  // .requestMatchers("/events/**").hasRole(AuthorityRole.USER.toString())
  // .requestMatchers("/users/**").hasRole(AuthorityRole.USER.toString())
  // .requestMatchers("/follows/**").hasRole(AuthorityRole.USER.toString())
  // .requestMatchers("/steps/**").hasRole(AuthorityRole.USER.toString())
  // .requestMatchers("/h2-console/**").permitAll())
  // .httpBasic(Customizer.withDefaults()) // for basic request for APIs etc
  // .csrf(crsf -> crsf.disable())
  // .headers(headers -> headers.frameOptions(frameOptions ->
  // frameOptions.disable())); // solves "localhost refusted
  // // to connect" in h2-console
  //
  // return http.build();
  // }

  // @Bean
  // UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
  // User.UserBuilder users = User.builder();
  //
  // UserDetails userA = users
  // .username("userA")
  // .password(passwordEncoder.encode("a@123"))
  // .roles(AuthorityRole.USER.toString())
  // .build();
  //
  // UserDetails userB = users
  // .username("userB")
  // .password(passwordEncoder.encode("b@344"))
  // .roles(AuthorityRole.NON_USER.toString())
  // .build();
  //
  // UserDetails userC = users
  // .username("userC")
  // .password(passwordEncoder.encode("c@666"))
  // .roles(AuthorityRole.USER.toString())
  // .build();
  //
  // // InMemoryUserDetailsManager is implementing UserDetailsService, thats why
  // its
  // // allowed to return it
  // return new InMemoryUserDetailsManager(userA, userB, userC);
  // }
  //

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
