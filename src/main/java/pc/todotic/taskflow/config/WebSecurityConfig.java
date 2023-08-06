package pc.todotic.taskflow.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pc.todotic.taskflow.security.JwtFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)//Desactivamos para que otro origen pueda consumir nustra API
                .authorizeHttpRequests(//definir reglas de proteccion de recursos
                        //definimos las rutas y los roles que pueden acceder basado en la API
                        a -> a
                                .requestMatchers("/api/auth/**")//Indicamos endpoint para solicitud
                                .permitAll()//Permitimos a todos
                                .anyRequest()//Permite cualquier solicitud
                                //.permitAll()//Permitir a todos y desabilita la proteccion a todos los endPoints
                                .authenticated()//Restringe el ingreso a usuarios autenticados
                        )
                .sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //configurar politica de creacion de sessiones: SIN ESTADO
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))//Muestra el estado de no autorizado cuando no se esta autentificado
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
}
