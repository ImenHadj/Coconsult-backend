package com.bezkoder.springjwt.security;

import com.bezkoder.springjwt.models.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bezkoder.springjwt.security.jwt.AuthEntryPointJwt;
import com.bezkoder.springjwt.security.jwt.AuthTokenFilter;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
// (securedEnabled = true,
// jsr250Enabled = true,
// prePostEnabled = true) // by default
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

//  @Override
//  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());

      return authProvider;
  }

//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.cors().and().csrf().disable()
//      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//      .antMatchers("/api/test/**").permitAll()
//      .anyRequest().authenticated();
//
//    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeRequests(auth ->
          auth.requestMatchers("/api/auth/**").permitAll()
              .requestMatchers("/api/test/**").permitAll()
                  .requestMatchers("/api/users/**").permitAll()
                  .requestMatchers("/api/test/user" ).permitAll()

                  .requestMatchers("/api/users/**").permitAll()
                  .requestMatchers("/api/users/statistics/role").permitAll()

                  .requestMatchers("/api/users/forgotPassword").permitAll() // Autoriser l'accès sans authentification
                  .requestMatchers("/perfomanceEmpl/average-by-criteria").permitAll() // Autoriser l'accès sans authentification

                  .requestMatchers("/coconsult/**").permitAll()
                  .requestMatchers("/recrutement/**").permitAll()
                  .requestMatchers("/DetailsRect/**").permitAll()

                            .requestMatchers("/", "/static/**").permitAll()

                    .requestMatchers("/","/favicon.ico").permitAll()
                    .requestMatchers("/", "/index.html", "/static/**").permitAll()
                     .requestMatchers("/", "/login.html", "/static/**").permitAll()
                      .requestMatchers("/", "/register.html", "/static/**").permitAll()
                      .requestMatchers("/", "/videocall.html", "/static/**").permitAll()
                    // Autoriser l'accès aux API de connexion, déconnexion et gestion des utilisateurs
                    .requestMatchers("/api/v1/users/login/**").permitAll()
                         .requestMatchers("/","/api/v1/users/logout/**").permitAll()
                      .requestMatchers("/","/api/v1/users/**").permitAll()

    .requestMatchers("/","/api/new-resource/**").permitAll() // Autoriser l'accès sans authentification
                            .requestMatchers("/stock/**").permitAll()
                            .requestMatchers("/commande/**").permitAll()
                            .requestMatchers("/fournisseur/**").permitAll()
                            .requestMatchers("/reclamation/**").permitAll()
                            .requestMatchers("/resource/**").permitAll()
                            .requestMatchers("/resource/removeResource").permitAll()
                            .requestMatchers("/resource/resources/{id}/image").permitAll()
                            .requestMatchers("/resource/add-resource").permitAll()
                  .requestMatchers("/Clients/**").permitAll()
                  .requestMatchers("/Clients/addcontrat/**").permitAll()
                  .requestMatchers("/Clients/addpaiment/**").permitAll()
                  .requestMatchers("/absence/**").permitAll() // Autoriser l'accès sans authentification
                  .requestMatchers("/cloudinary/**").permitAll() // Autoriser l'accès sans authentification
                  .requestMatchers("/Conge/**").permitAll() // Autoriser l'accès sans authentification
                  .requestMatchers("/contratEmpl/**").permitAll()
                  .requestMatchers("/SalaireEmpl/**").permitAll() // Autoriser l'accès sans authentificat// Autoriser l'accès sans authentification
                  .requestMatchers("/departement/**").permitAll() // Autoriser l'accès sans authentification
//                  .requestMatchers("/employee/retrieveAll").hasAuthority("ROLE_ADMIN")// Autoriser l'accès sans authentification
                  .requestMatchers("/employee/**").permitAll()// Autoriser l'accès sans authentification
                  .requestMatchers("/Note/**").permitAll() // Autoriser l'accès sans authentification
                  .requestMatchers("/perfomanceEmpl/**").permitAll()// Autoriser l'accès sans au
    .anyRequest().authenticated()
            );
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

}
