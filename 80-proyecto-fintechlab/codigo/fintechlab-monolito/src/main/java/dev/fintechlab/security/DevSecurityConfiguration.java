package dev.fintechlab.security;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration @Profile("dev")
class DevSecurityConfiguration { @Bean SecurityFilterChain dev(HttpSecurity http)throws Exception{return http.csrf(c->c.disable()).authorizeHttpRequests(a->a.anyRequest().permitAll()).build();} }
