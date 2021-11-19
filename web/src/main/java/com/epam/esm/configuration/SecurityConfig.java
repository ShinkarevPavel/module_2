package com.epam.esm.configuration;

import com.epam.esm.entity.Role;
import com.epam.esm.exception.ApplicationExceptionHandler;
import com.epam.esm.exception.JsonResponseSender;
import com.epam.esm.exception.JwtAuthenticationException;
import com.epam.esm.security.JwtConfigure;
import com.epam.esm.security.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigure jwtConfigure;
    private final JwtTokenFilter jwtTokenFilter;
    private final ApplicationExceptionHandler applicationExceptionHandler;
    private final JsonResponseSender jsonResponseSender;

    public SecurityConfig(JwtConfigure jwtConfigure,
                          JwtTokenFilter jwtTokenFilter,
                          ApplicationExceptionHandler applicationExceptionHandler,
                          JsonResponseSender jsonResponseSender) {
        this.jwtConfigure = jwtConfigure;
        this.jwtTokenFilter = jwtTokenFilter;
        this.applicationExceptionHandler = applicationExceptionHandler;
        this.jsonResponseSender = jsonResponseSender;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/certificates/**").permitAll()
                .antMatchers("/api/v3/auth/**").permitAll()
                .antMatchers("/api/v1/tags/**").hasAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(
                        (httpServletRequest, httpServletResponse, e) -> handleError(httpServletRequest, httpServletResponse)
                )
                .and()
                .apply(jwtConfigure);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException {
                    Object responseObject = applicationExceptionHandler
                    .handleJwtAuthenticationException(new JwtAuthenticationException("auth.error.incorrect_token", HttpStatus.UNAUTHORIZED),
                            request.getLocale());
            jsonResponseSender.send(response, responseObject);
    }

}
