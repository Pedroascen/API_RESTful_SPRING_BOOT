package pc.todotic.taskflow;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository){//Mejora de la inyeccion de dependencia, para un codigo testeable
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping//metodo para obtener lista de usuarios
    List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")//metodo para obtener usuario por id
    Usuario obtener(@PathVariable Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping//metodo para crear nuevo usuario
    Usuario crear(@RequestBody Usuario usuario){
        usuarioRepository.save(usuario);
        return usuario;
    }

    @PutMapping("/{id}")
    Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario form){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        usuario.setNombre(form.getNombre());
        return usuarioRepository.save(usuario);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")//metodo para eliminar usuario
    void eliminar(@PathVariable Integer id){
        usuarioRepository.deleteById(id);
    }
}
