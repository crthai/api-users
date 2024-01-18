package com.example.config;
import com.example.security.JWTAuthenticationFilter;
import com.example.security.JWTAuthorizationFilter;
import com.example.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


    private final  Environment env;

    private final UserDetailsService detailsService;

    private final JWTUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**"
    };

    @Autowired
    public SecurityConfig(Environment env, UserDetailsService detailsService, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.env = env;
        this.detailsService = detailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.csrf().disable()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//        return httpSecurity.build();
//    }
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("aamir")
//                .password("123")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

//    @Bean
//    protected SecurityFilterChain filterChain(
//            HttpSecurity http,
//            CustomBasicAuthFilter customBasicAuthFilter) throws Exception {
//
//        http
//                .authorizeHttpRequests(
//                        authorizeConfig -> {
//                            authorizeConfig.anyRequest().authenticated();
//                        }
//                )
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(customBasicAuthFilter, BasicAuthenticationFilter.class)
//                .csrf().disable();
//
//        return http.build();
@Bean
protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
    if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
        http.headers((headers) ->
                headers.frameOptions((frameOptions) -> frameOptions.disable())
        );
    }
    http.authenticationManager(authenticationManager).authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/*")).permitAll()
                    .anyRequest().authenticated()
            )
            .addFilter(new JWTAuthenticationFilter(authenticationManager, jwtUtil))
            .addFilter(new JWTAuthorizationFilter(authenticationManager, jwtUtil, detailsService))
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);
    return http.build();
}

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(passwordEncoder);
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}