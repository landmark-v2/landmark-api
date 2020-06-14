package com.landmark.app.config;

import com.landmark.app.security.authentication.TokenAuthentication;
import com.landmark.app.security.filter.StatelessAuthenticationFilter;
import com.landmark.app.security.filter.StatelessLoginFilter;
import com.landmark.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static com.landmark.app.utils.constants.Constants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private TokenAuthentication tokenAuthentication;

    @Autowired
    public SecurityConfig(UserService userService, TokenAuthentication tokenAuthentication) {
        this.userService = userService;
        this.tokenAuthentication = tokenAuthentication;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling()
                .and().anonymous()
                .and().servletApi()
                .and().headers().cacheControl();

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST, "/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/review/**").permitAll()
                .antMatchers(HttpMethod.GET, "/review/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/review/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/review/**").permitAll()
                .antMatchers(HttpMethod.POST, "/search/**").permitAll()
                .antMatchers(HttpMethod.GET, "/code/**").permitAll()
                .antMatchers(HttpMethod.POST, "/info/**").hasAnyRole(ADMIN, DEV)
                .antMatchers(HttpMethod.GET, "/info/**").hasAnyRole(ADMIN, DEV)
                .antMatchers(HttpMethod.PUT, "/info/**").hasAnyRole(ADMIN, DEV)
                .antMatchers(HttpMethod.DELETE, "/info/**").hasAnyRole(ADMIN, DEV)
                .antMatchers(HttpMethod.POST, "/faq/**").hasRole(DEV)
                .antMatchers(HttpMethod.GET, "/faq/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/faq/**").hasRole(DEV)
                .antMatchers(HttpMethod.DELETE, "/faq/**").hasRole(DEV)
                .antMatchers(HttpMethod.POST, "/qna/**").permitAll()
                .antMatchers(HttpMethod.GET, "/qna/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/qna/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/qna/**").permitAll()
                .antMatchers(HttpMethod.POST, "/notice/**").hasRole(DEV)
                .antMatchers(HttpMethod.GET, "/notice/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/notice/**").hasRole(DEV)
//                .antMatchers(HttpMethod.DELETE, "/notice/**").hasRole(DEV)
                .anyRequest().authenticated();


        http.addFilterBefore(this.corsFilter(),UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(
                new StatelessLoginFilter("/login", tokenAuthentication, userService, authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(
                new StatelessAuthenticationFilter(tokenAuthentication),
                UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("auth-token");
        config.setMaxAge(36000L);
        config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}