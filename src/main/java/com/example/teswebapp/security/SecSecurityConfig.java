package com.example.teswebapp.security;

//import com.baeldung.persistence.dao.UserRepository;
//import com.baeldung.security.CustomRememberMeServices;
//import com.baeldung.security.google2fa.CustomAuthenticationProvider;
//import com.baeldung.security.google2fa.CustomWebAuthenticationDetailsSource;
//import com.baeldung.security.location.DifferentLocationChecker;
import com.example.teswebapp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

//import com.maxmind.geoip2.DatabaseReader;
//import com.maxmind.geoip2.exception.GeoIp2Exception;


//@Order(2)
//@ComponentScan(basePackages = { "com.example.teswebapp.security" })
//@ImportResource({ "classpath:webSecurityConfig.xml" })
@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    private MyUserDetailsService myUserDetailsService;

    public SecSecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username,password,enabled "
//                + "from users "
//                + "where username = ?")
//                .authoritiesByUsernameQuery("select username,authority "
//                        + "from authorities "
//                        + "where username = ?")
        ;

//        auth.inMemoryAuthentication()
//                .withUser("user1").password(encoder().encode("user1Pass")).roles("USER")
//                .and()
//                .withUser("user2").password(encoder().encode("user2Pass")).roles("USER")
//                .and()
//                .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        //disable security to not asking to login at this time (not needed for signup)
        //security.httpBasic().disable();

        security
                .authorizeRequests()

                .antMatchers("/**").permitAll()

//                .antMatchers("/webjars/**","/css/**","/", "/index",
//                        "/signup","/adduser","/delete/**","/email","/sendEmail","/regitrationConfirm/**").permitAll()
//                .antMatchers("/update/**","/edit/**","/update pwd/**","/edit pwd/**").hasAnyAuthority("USER")
//                .antMatchers("/delete/**").hasAnyAuthority("ADMIN")

//                .antMatchers("/", "/index", "/signup","/adduser",
//                        "/login","/update/**","/edit/**","/update pwd/**","/edit pwd/**","/delete/**",
//                        "/webjars/**","/css/**").permitAll()


                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/index");




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

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        daoAuthenticationProvider.setUserDetailsService(this.myUserDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
