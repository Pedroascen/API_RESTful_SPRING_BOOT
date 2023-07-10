package pc.todotic.taskflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    //metodo para obtener lista de usuarios
    @RequestMapping(value = "/api/usuarios", method = RequestMethod.GET)
    List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    //metodo para obtener usuario por id
    @RequestMapping(value = "/api/usuarios/{id}", method = RequestMethod.GET)
    Usuario obtener(@PathVariable Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }

    //metodo para crear nuevo usuario
    @RequestMapping(value = "/api/usuarios", method = RequestMethod.POST)
    Usuario crear(@RequestBody Usuario usuario){
        usuarioRepository.save(usuario);
        return usuario;
    }

    //metodo para actualizar usuario
    @RequestMapping(value = "/api/usuarios/{id}", method = RequestMethod.PUT)
    Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario form){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        usuario.setNombre(form.getNombre());
        Usuario u = usuarioRepository.save(usuario);
        return u;
    }

    //metodo para eliminar usuario
    @RequestMapping(value = "/api/usuarios/{id}",method = RequestMethod.DELETE)
    void eliminar(@PathVariable Integer id){
        usuarioRepository.deleteById(id);
    }
}
