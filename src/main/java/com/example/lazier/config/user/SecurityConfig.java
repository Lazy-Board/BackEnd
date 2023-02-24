package com.example.lazier.config.user;

import com.example.lazier.exception.user.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring()
        .antMatchers(HttpMethod.GET, "/user/email-auth")
        .antMatchers(HttpMethod.POST, "/user/find-password")
        .antMatchers(HttpMethod.POST, "/user/signup")
        .antMatchers(HttpMethod.POST, "/user/login")
        .antMatchers(HttpMethod.GET, "/user/login/**")
        .antMatchers(HttpMethod.POST, "/user/reissue")
        .antMatchers(HttpMethod.GET, "/login")
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .antMatchers("/h2-console/**");

  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder getPasswordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  } //비밀번호 암호화


  // CORS
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.addAllowedOrigin("http://35.77.78.168:5173/");
    corsConfiguration.addAllowedOrigin("http://localhost:3000/");
    corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
    corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
    corsConfiguration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().
        csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .headers().frameOptions().disable()
        .and()
        .authorizeRequests()
        .antMatchers("/user/signup",
            "/favicon.ico",
            "/user/login",
            "/user/email-auth",
            "/user/find-password",
            "/user/reissue",
            "/h2-console",
            "/youtube",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/user/login/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(customAuthenticationEntryPoint)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(new JwtFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests();
    return http.build();
  }

}
