package net.engineering.digest.journalapp.config.security;

import net.engineering.digest.journalapp.config.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    // This is for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/journal/**").authenticated()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/redis/**").authenticated()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

                //.and().httpBasic(); This is used in case of basic authentication but we are using JWT so we will not use it

        // Disable CSRF for simplicity in this example (not recommended for production)
       // http.csrf().disable();

        // Set session management to stateless since we're using basic authentication - not recommended for production, but fine for this example
        // ( we kow spring security will manage the session that why we disable it)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();


        // IMPORTANT -: Add the JWT filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //this is for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    // For Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // This is for creating Bean of AuthenticationManager
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
