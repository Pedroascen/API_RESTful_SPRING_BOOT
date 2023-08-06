package pc.todotic.taskflow.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenProvider {//clase encargada de generar y decifrar token usando contrasenia

    private Long tokenValidityInSeconds = 2_592_000L;//tiempo de vida del token 30 dias en segundos
    //Contrasena en texto plano
    private String tokenSecret="LYTvMBxds49KQzMzf5XJ4bzkExNsmuqXj3nfM2j3UBhGuVaDuKgWfEykNAKWADNFt4Uz9eJ4Eguy3EfNdygEPDfL5q3TjfUMVVszkDJQerp7tk9GMcUzfyrMKA8BGq4f97A7ZgpPEMgbLdH9Guz5zyVCtNJUqyQN7n25NaQVdFeTguhjVAH5nAEzqZwSCVKbZs9A96aCWLSgY3wszU9dhUZwbqQbdZRvjb4cfG2EC76wfUCAevDajZU6SRHAJdwN";
    private byte[] secretByte;
    private SecretKey key;

    public TokenProvider(){
        //generacion de llave
        secretByte = Decoders.BASE64.decode(tokenSecret);
        key = Keys.hmacShaKeyFor(secretByte);
    }

    public String crearToken(Authentication authentication){
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

        //algoritmo para obtener fecha de expiracion
        long fechaActual = new Date().getTime();
        Date fechaExpiracion = new Date(fechaActual + (tokenValidityInSeconds * 1_000));//se multiplica por 1000 para pasar milisegundos en segundos y se suma a la fechaActual
        //Obtener autoridades y permisos
        String autoridades = authentication
                .getAuthorities()//roles -> ROL_, permisos -> Permiso_
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        return Jwts
                .builder()
                .setSubject(authentication.getName())//generamos el auth del token
                .claim("auth",autoridades)
                .setExpiration(fechaExpiracion)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication obtenerAutenticacion(String token) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            //desifra el token
            List<SimpleGrantedAuthority> authorities = Arrays
                    .stream(claims.get("auth").toString().split(","))
                    .filter(auth -> !auth.trim().isEmpty())
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            //guarda la informacion en el objeto principal
            User principal = new User(claims.getSubject(),"",authorities);
            return new UsernamePasswordAuthenticationToken(principal,token,authorities);
        }catch (JwtException e){
            return null;
        }
    }
}
