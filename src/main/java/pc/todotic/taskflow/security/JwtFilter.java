package pc.todotic.taskflow.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter  extends GenericFilterBean {

    private final TokenProvider tokenProvider;
    @Override//metodo para filtrar seguridad del access token
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        //verificamos que exista el bearerToken
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")){
            String token = bearerToken.substring(7);//se extraer el token, sin prefijo "bearer "
            Authentication authentication = tokenProvider.obtenerAutenticacion(token);
            //comprobamos si autentication es diferente de null para establecer el objeto auth en el contexto de seguridad
            if(authentication != null){
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
