package pc.todotic.taskflow.controlador;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pc.todotic.taskflow.controlador.dto.AccessTokenDTO;
import pc.todotic.taskflow.controlador.dto.LoginDTO;
import pc.todotic.taskflow.security.TokenProvider;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j//inyecta un objeto log para mostrar registro
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;//Objeto manejado por Sprint para autentificar
    private final TokenProvider tokenProvider;
    @PostMapping("/token")//Respuesta de objeto que se serializa
   ResponseEntity<AccessTokenDTO> obtenerToken(@RequestBody LoginDTO loginDTO){
        log.info("Login: {}",loginDTO);
        //crear instancia de UsernamePasswordAuthenticationToken para el proceso de autenticacion
        UsernamePasswordAuthenticationToken UserPAT = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getClave()
        );
        //establecemos el objeto a nivel autentication
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(UserPAT);
        //Establecemos el objeto al contexto de seguridad de sprint
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.crearToken(authentication);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .body(new AccessTokenDTO(token));
    }
}
