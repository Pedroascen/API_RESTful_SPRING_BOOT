package pc.todotic.taskflow.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pc.todotic.taskflow.modelo.Usuario;
import pc.todotic.taskflow.repositorio.UsuarioRepository;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    //inyectamos la dependencia del repositorio de Usuario
    private final UsuarioRepository usuarioRepository;
    //Servicio para que spring security utilice esta implementacion para cargar el usuario por email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario con ese email no existe."));

      return User.withUsername(email)
              .password(usuario.getClave())
              .roles(usuario.getRol().name())
              .build();
    }
}
