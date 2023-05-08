package balu.pizzarest.pizzaproject.config;

import balu.pizzarest.pizzaproject.services.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author Sergii Bugaienko
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailService personDetailService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(PersonDetailService personDetailService, JWTFilter jwtFilter) {
        this.personDetailService = personDetailService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v2**", "/v3/**", "/h2-console/**").permitAll()

//                .antMatchers("/auth/registration", "/auth/login", "/error", "/", "/menu",
//                        "/about", "/contact", "/cafe/pizza/*", "/pizza/addToFav/*", "/cafe", "/cafe/**").permitAll()
//                .antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/scss/**", "/favicon.ico").permitAll()
                .antMatchers("/api/auth/login", "/api/auth/signup", "api/users/login", "/api/auth/**").permitAll()
//                .antMatchers("/api/**").permitAll()
//                .antMatchers("/pizza/checkPrice/*", "pizza/setPrice/*").hasAnyRole("ADMIN")
//                .antMatchers("/user/*").hasAnyRole("USER", "ADMIN")
//                .anyRequest().hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/auth/login")
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/", true)
//                .failureUrl("/auth/login?error")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/auth/login")
//                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v2/api-docs/**");
         }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
