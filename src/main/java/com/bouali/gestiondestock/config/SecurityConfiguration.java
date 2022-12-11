package com.bouali.gestiondestock.config;

import com.bouali.gestiondestock.services.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    private ApplicationRequestFilter applicationRequestFilter; // hethi emte3 do filter internal !!!


//    private static final String[] AUTH_WHITELIST = {
//            "/swagger-resources/**",
//            "/swagger-ui.html",
//            "/v2/api-docs",
//            "/webjars/**",
//            "/**/**/auth/authenticate"
//    };
private static final String[] AUTH_WHITELIST = {
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**",
        "/**/**/auth/authenticate"
};
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO AuthenticationManagerBuilder c 'est le manager d'authentification
        // TODO on va dire Que AuthenticationManagerBuilder il va utiliser mon service applicationUserDetailsService
        auth.userDetailsService(applicationUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        //http
                //.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .authorizeRequests().antMatchers("/**/authenticate",
                        "**/entreprises/create",
                        "**/utilisateurs/all",
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "swagger-ui/**").permitAll() // TODO ces URL SONT PERMIT SANS AVOIR ETRE AUTHENTIFIER (sans token)
                .anyRequest().authenticated()
                //.anyRequest().permitAll()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;

        http.addFilterBefore(applicationRequestFilter , UsernamePasswordAuthenticationFilter.class) ;

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return NoOpPasswordEncoder.getInstance();
    }
}
