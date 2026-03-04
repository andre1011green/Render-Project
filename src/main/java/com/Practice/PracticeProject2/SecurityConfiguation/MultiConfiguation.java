package com.Practice.PracticeProject2.SecurityConfiguation;

import com.Practice.PracticeProject2.Service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

/**
 * The purpose of this class is to authenticate users
 */

@Configuration
@EnableWebSecurity
public class MultiConfiguation {
    /**
     * customUserDetailsService is an object that uses customUserDetail along with Spring Security. It loads user-specific data
     */
    @Autowired
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * The purpose of this constructor is to implement dependency injection and it will make testing easier
     *
     * @param customUserDetailsService
     */
    public MultiConfiguation(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * The purpose of an AuthenticationManager is to  processes authentication requests. When a user tries to log in,
     * the AuthenticationManager validates their credentials.  It integrates with various authentication providers,
     * like DaoAuthenticationProvider
     *
     * @param httpSecurity
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    /**
     * The purpose of the PasswordEncoder is to encode plain text passwords supplied by the user
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * The purpose of a DaoAuthenticationProvider is to interact with a database or any persistent storage to retrieve
     * user authentication details. When a user attempts to log in, it fetches the necessary details (like username, password, roles)
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService,
                                                  PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * The purpose of filterChainRed is to configure security for this web application. It specifies which endpoints
     * are accessible by all users and which endpoints can only be access by managers. This filter chain also
     * specifies a custom login and logout pages to use, along with a default successful URL to use. It specifies what to do
     * if a login error occurs.
     *
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChainRed(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers("/admin/managerLogin", "/admin/managerError").permitAll()
                        .requestMatchers("/CSS/**").permitAll()
                        .requestMatchers("/IMAGES/**").permitAll()
                        .requestMatchers("/JAVASCRIPT/**").permitAll()
                        .requestMatchers("/admin/managerLogout").permitAll()
                        .requestMatchers("/admin/managerHome").hasRole("ADMIN")
                        .requestMatchers("/admin/manageEmployees").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/admin/managerLogin.html").permitAll()
                                .loginProcessingUrl("/admin/managerLogin").permitAll()
                                .defaultSuccessUrl("/admin/managerHome", true)
                                .failureUrl("/admin/managerError.html").permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/admin/managerLogout").permitAll()
                        .logoutSuccessUrl("/admin/managerLogout.html")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }

    /**
     * The purpose of filterChainBlue is to configure security for this web application. It specifies which endpoints
     * are accessible by all users and which endpoints can only be access by employees. This filter chain also
     * specifies a custom login and logout pages to use, along with a default successful URL to use. It specifies what to do
     * if a login error occurs.
     *
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChainBlue(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers("/", "/user/index", "/user/error").permitAll()
                        .requestMatchers("/user/logout").permitAll()
                        .requestMatchers("/CSS/**").permitAll()
                        .requestMatchers("/IMAGES/**").permitAll()
                        .requestMatchers("/JAVASCRIPT/**").permitAll()
                        .requestMatchers("/user/home").hasRole("EMPLOYEE")
                        .anyRequest().authenticated()

                );

        http.exceptionHandling(exception ->
                exception.accessDeniedHandler(new CustomAccessDeniedHandler())
        )


                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/user/index.html").permitAll()
                                .loginProcessingUrl("/user/index").permitAll()
                                .defaultSuccessUrl("/user/home", true)
                                .failureUrl("/user/error.html").permitAll()

                )

                .logout(logout -> logout
                        .logoutUrl("/user/logout").permitAll()
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}

