
package com.fran.ogascon;

import com.fran.ogascon.servicios.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SeguridadWeb {
    
    //Security Filter CHain
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity
//                .csrf(csrf-> csrf.disable()) // ESTO DEBE CHEQUEARSE PORQUE SE NECESITA EN APP MVC
//                .httpBasic(Customizer.withDefaults()) // SOLO VA A PEDIR USER Y PASSWORD
//                .sessionManagement((session) ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //NO EXPIRA LA SESION
//                .authorizeHttpRequests(http->{
////                        CONFIGURAR los endpoints PUBLICOS
//                        http.requestMatchers(HttpMethod.GET,"/login").permitAll();
//                        
//                       //CONFIGURAR los endpoints PRIVADOS
//                        http.requestMatchers(HttpMethod.GET).hasAuthority(authority);
//                        
//                        //CONFIGURAR los endpoints NO ESPECIFICADOS denegando
//                        http.anyRequest().denyAll();                        
//                        
////                        CONFIGURAR los endpoints NO ESPECIFICADOS  autenticados
//                        http.anyRequest().authenticated();      
//                } )
//                .build();
//    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Puedes habilitarlo si manejas formularios en MVC
                .formLogin(form -> form // Permitir autenticación mediante formulario
                    .loginPage("/login") // Página de login personalizada
                    .usernameParameter("email")  // Indicamos a Spring que el campo del login es 'email'
                    .defaultSuccessUrl("/", true) // Redirigir a la raíz (index) tras login exitoso
                    .failureUrl("/login?error=true") // Redirige al login si la autenticación falla     
                    .permitAll()) // Permitir acceso público a la página de login
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/css/**", "/js/**").permitAll() // Acceso público a login y recursos estáticos
                    .anyRequest().authenticated()) // El resto de las solicitudes requieren autenticación
                .build();
    }
   
    @Bean
    public AuthenticationManager auhtenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailServiceImpl){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailServiceImpl);
        return provider;
    }
  
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    } 
    
}
