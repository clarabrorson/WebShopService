package com.Molndal.WebShopService.Config;

import com.Molndal.WebShopService.Utils.KeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

//Klass som konfigurerar säkerheten för Spring Security
//Den är annoterad med @Configuration för att indikera att det är en konfigurationsklass för Spring Framework
@Configuration
public class SecurityConfig {

    //Instansvariabel till en KeyProperties-instans
    private final KeyProperties keys;

    //Konstruktor som tar in en KeyProperties-instans som parameter och lagrar den i keys-instansvariabeln.
    public SecurityConfig(KeyProperties keys) {this.keys = keys;}

    //Denna metod definierar en bean för PasswordEncoder, som används för att koda lösenord.
    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    //Denna metod definierar en bean för AuthenticationManager, som är ansvarig för att validera användarautentisering.
    //DaoAuthenticationProvider är en implementering av AuthenticationProvider som hämtar användardetaljer från en UserDetailsService.
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    //Denna metod definierar en bean för SecurityFilterChain, som är en kedja av säkerhetsfilter som tillämpas på inkommande HTTP-förfrågningar.
    //Den innehåller en HttpSecurity som definierar säkerhetsreglerna för applikationen.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    //Dessa förfrågningar är tillåtna utan autentisering.
                    auth.requestMatchers("/webshop/auth/**").permitAll();
                    auth.requestMatchers("/webshop/articles").permitAll();

                    //Dessa förfrågningar måste vara autentiserade med "USER" eller "ADMIN".
                    auth.requestMatchers("/webshop/articles/").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers("/webshop/user/**").hasAnyRole("ADMIN", "USER");

                    //Dessa förfrågningar måste vara autentiserade med rollen "ADMIN".
                    auth.requestMatchers("/webshop/articles/admin/**").hasRole("ADMIN");

                    //Alla andra förfrågningar måste vara autentiserade. Om en förfrågan inte är autentiserad kommer den att avvisas.
                    auth.anyRequest().authenticated();
                });
        //OAuth2 Resource Server stöd aktiveras och konfigureras för att använda JWTs.
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        //Sessionshanteringen konfigureras för att inte skapa några sessioner
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //Konstruktionen av SecurityFilterChain är klar och instansen returneras.
        return http.build();
    }

    //Denna metod definierar en bean för JwtDecoder, som används för att avkoda JWTs.
    @Bean
    public JwtDecoder jwtDecoder() {
        //NimbusJwtDecoder.withPublicKey används för att skapa en JwtDecoder som använder den offentliga nyckeln från KeyProperties för att avkoda JWTs.
        //.build() metoden används för att skapa JwtDecoder instansen.
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        //En RSA-key skapas med den offentliga och privata nyckeln från KeyProperties.
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        //En JWKSource skapas från den genererade RSA-key.
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        //En JwtEncoder skapas med den genererade JWKSource.
        return new NimbusJwtEncoder(jwks);
    }

    //Denna metod definierar en bean för JwtAuthenticationConverter, som används för att konvertera en JWT till en Authentication-instans.
    //Detta behövs för att autentisera användaren och tilldela behörigheter baserat på JWT.
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        //Skapar en JwtGrantedAuthoritiesConverter, som används för att konvertera JWT-anspråk till GrantedAuthority-instanser.
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //Sätter anspråksnamnet för auktoriteter till "roles". Detta innebär att auktoriteter kommer att extraheras från "roles"-anspråket i JWT.
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        //Sätter prefixet för auktoriteter till "ROLE_". Detta innebär att alla auktoriteter som extraheras från JWT kommer att ha "ROLE_" som prefix.
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        //Skapar en JwtAuthenticationConverter, som används för att konvertera en JWT till en Authentication-instans.
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        //Sätter JwtGrantedAuthoritiesConverter för JwtAuthenticationConverter.
        //Detta innebär att JwtAuthenticationConverter kommer att använda JwtGrantedAuthoritiesConverter för att konvertera JWT-anspråk till GrantedAuthority-instanser.
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
}
