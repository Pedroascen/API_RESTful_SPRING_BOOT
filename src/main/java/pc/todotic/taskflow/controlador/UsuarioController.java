package pc.todotic.taskflow.controlador;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pc.todotic.taskflow.exception.BadRequestException;
import pc.todotic.taskflow.modelo.Tarea;
import pc.todotic.taskflow.modelo.Usuario;
import pc.todotic.taskflow.repositorio.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping//metodo para obtener lista de usuarios
    List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")//metodo para obtener usuario por id
    Usuario obtener(@PathVariable Integer id){
        return usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping//metodo para crear nuevo usuario
    Usuario crear(@Validated @RequestBody Usuario usuario){
        boolean emailYaEstaRegistrado = usuarioRepository.existsByEmail(usuario.getEmail());

        if (emailYaEstaRegistrado) {
            throw new BadRequestException("El email ya fue registrado.");
        }

        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        usuarioRepository.save(usuario);
        return usuario;
    }

    @PutMapping("/{id}")
    Usuario actualizar(@PathVariable Integer id, @Validated @RequestBody Usuario form){

        boolean emailYaRegistrado = usuarioRepository.existsByEmailAndIdNot(form.getEmail(),id);

        if(emailYaRegistrado){
            throw new BadRequestException("El email ya fue registrado.");
        }

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuario.setNombre(form.getNombre());
        usuario.setEmail(form.getEmail());
        usuario.setClave(passwordEncoder.encode(form.getClave()));
        usuario.setRol(form.getRol());
        return usuarioRepository.save(usuario);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")//metodo para eliminar usuario
    void eliminar(@PathVariable Integer id){
        usuarioRepository.deleteById(id);
    }

    @GetMapping("/{idUsuario}/tareas")
    List<Tarea> listarTareas(@PathVariable Integer idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(EntityNotFoundException::new);
        return usuario.getTarea();
    }

    @GetMapping("/paginar")//metodo para paginar los usuarios
    Page<Usuario> paginar(Pageable pageable){
        return usuarioRepository.findAll(pageable);
    }
}
