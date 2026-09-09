package dev.fintechlab.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import java.util.List;

@Configuration @Profile("secure") @EnableMethodSecurity
class SecureSecurityConfiguration {
    @Bean SecurityFilterChain secure(HttpSecurity http)throws Exception{return http.csrf(c->c.disable()).authorizeHttpRequests(a->a.requestMatchers("/actuator/health").permitAll().anyRequest().authenticated()).oauth2ResourceServer(o->o.jwt(Customizer.withDefaults())).build();}
    @Bean JwtDecoder jwtDecoder(@Value("${fintechlab.security.issuer}")String issuer,@Value("${fintechlab.security.audience}")String audience){
        NimbusJwtDecoder decoder=NimbusJwtDecoder.withIssuerLocation(issuer).build();
        OAuth2TokenValidator<Jwt> defaults=JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> aud=new JwtClaimValidator<List<String>>("aud",values->values!=null&&values.contains(audience));
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(defaults,aud));return decoder;
    }
}
