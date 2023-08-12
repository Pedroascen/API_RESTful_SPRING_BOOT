package pe.todotic.taskflow.controlador;

import io.swagger.v3.oas.annotations.tags.Tag;
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
import pe.todotic.taskflow.controlador.dto.AccessTokenDTO;
import pe.todotic.taskflow.controlador.dto.LoginDTO;
import pe.todotic.taskflow.security.TokenProvider;

@Tag(name = "auth", description = "Endpoint para la gestion de un token.")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @PostMapping("/token")
    ResponseEntity<AccessTokenDTO> obtenerToken(@RequestBody LoginDTO loginDTO) {
        log.info("Login: {}", loginDTO);

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getClave()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePAT);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.crearToken(authentication);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new AccessTokenDTO(token));
    }

}
