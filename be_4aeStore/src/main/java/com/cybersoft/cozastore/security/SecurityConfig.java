package com.cybersoft.cozastore.security;

import com.cybersoft.cozastore.filter.JwtFilter;
import com.cybersoft.cozastore.provider.CustomAuthenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //custom spring security
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    //tao ra chuan ma hoa password la lua tru o IOC
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Autowired
    CustomAuthenProvider customAuthenProvider;

    //chỉ định AuthenticationManager sử dụng CustomAuthenProvider
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }


    //tao danh sach ao luu tren RAM dung de chung thuc
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder.encode("123456"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user); //luu tru 2 user nay tren RAM
//    }

    //cau hinh chung thuc cho cac link chi dinh cho security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                    .requestMatchers("/login/**").permitAll()
                    .requestMatchers("/file/**").permitAll()
                    .requestMatchers("/cart/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/product").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/product").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/cart").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}