package com.example.teswebapp.security;

//import com.baeldung.persistence.dao.UserRepository;
//import com.baeldung.security.CustomRememberMeServices;
//import com.baeldung.security.google2fa.CustomAuthenticationProvider;
//import com.baeldung.security.google2fa.CustomWebAuthenticationDetailsSource;
//import com.baeldung.security.location.DifferentLocationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
        import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

//import com.maxmind.geoip2.DatabaseReader;
//import com.maxmind.geoip2.exception.GeoIp2Exception;


//@Order(2)
//@ComponentScan(basePackages = { "com.example.teswebapp.security" })
//@ImportResource({ "classpath:webSecurityConfig.xml" })
@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(encoder().encode("user1Pass")).roles("USER")
                .and()
                .withUser("user2").password(encoder().encode("user2Pass")).roles("USER")
                .and()
                .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        //disable security to not asking to login at this time (not needed for signup)
        //security.httpBasic().disable();

        security
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();




//        security
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/anonymous*").anonymous()
//                .antMatchers("/login*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login.html")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/homepage.html", true)
//                .failureUrl("/login.html?error=true")
//                .failureHandler(authenticationFailureHandler())
//                .and()
//                .logout()
//                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessHandler(logoutSuccessHandler());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
