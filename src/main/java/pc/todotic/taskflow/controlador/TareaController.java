package pc.todotic.taskflow.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pc.todotic.taskflow.modelo.Tarea;
import pc.todotic.taskflow.modelo.Usuario;
import pc.todotic.taskflow.repositorio.TareaRepository;
import pc.todotic.taskflow.repositorio.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    private TareaRepository tareaRepository;
    private UsuarioRepository usuarioRepository;

    public TareaController(TareaRepository tareaRepository,UsuarioRepository usuarioRepository){
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping//metodo para obtener todas las tareas
    List<Tarea> listar(){
        return tareaRepository.findAll();
    }

    @GetMapping("/{id}")//metodo para encontrar tarea por id
    Tarea obtener(@PathVariable Integer id){
        return tareaRepository.findById(id).orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Tarea crear(@RequestBody Tarea tarea) {
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setEstado(Tarea.Estado.CREADO);

        return tareaRepository.save(tarea);
    }

    @PutMapping("/{id}")
    Tarea actualizar(@PathVariable Integer id, @RequestBody Tarea form) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);

        tarea.setTitulo(form.getTitulo());
        tarea.setDescripcion(form.getDescripcion());
        tarea.setEstado(form.getEstado());
        tarea.setPrioridad(form.getPrioridad());
        tarea.setFechaLimite(form.getFechaLimite());

        return tareaRepository.save(tarea);
    }
    @PutMapping("/{idTarea}/asignarresponsable/{idUsuario}")
    Tarea asignarResponsable(@PathVariable Integer idTarea,@PathVariable Integer idUsuario){//metodo para asignar responsable a tarea
        Tarea tarea = tareaRepository.findById(idTarea).orElse(null);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        tarea.setResponsable_id(usuario);
        return tareaRepository.save(tarea);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")//metodo para eliminar tarea
    void eliminar(@PathVariable Integer id){
        tareaRepository.deleteById(id);
    }
}
