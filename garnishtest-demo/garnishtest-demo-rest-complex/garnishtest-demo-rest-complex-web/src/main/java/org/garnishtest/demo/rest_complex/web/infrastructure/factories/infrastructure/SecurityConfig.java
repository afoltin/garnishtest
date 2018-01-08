package org.garnishtest.demo.rest_complex.web.infrastructure.factories.infrastructure;

import org.garnishtest.demo.rest_complex.web.infrastructure.factories.dao.DaoMappersConfig;
import org.garnishtest.demo.rest_complex.web.service.security.SecurityAuthenticationProvider;
import org.garnishtest.demo.rest_complex.web.service.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
@Import(DaoMappersConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int PASSWORD_ENCODER_STRENGTH = 10;

    @Autowired
    private DaoMappersConfig daoMappersConfig;


    @Override
    public void configure(final WebSecurity web) throws Exception {
        //@formatter:off
        web
            .ignoring()
                .antMatchers(HttpMethod.POST, "/users")           // create user
                .antMatchers(HttpMethod.POST, "/users/*/tokens")  // create token (login)
                .and()
        ;
        //@formatter:on
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .csrf().disable()
            .rememberMe().disable()
            .anonymous().disable()
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .addFilterBefore(tokenAuthenticationFilter(), AnonymousAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
        ;
        //@formatter:on
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
        return new TokenAuthenticationFilter(
                authenticationManager(),
                authenticationEntryPoint()
        );
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(PASSWORD_ENCODER_STRENGTH);
    }

    @Bean
    public SecurityAuthenticationProvider authenticationProvider() throws Exception {
        return new SecurityAuthenticationProvider(
                this.daoMappersConfig.userMapper()
        );
    }


}
