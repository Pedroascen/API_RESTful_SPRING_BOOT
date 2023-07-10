package pc.todotic.taskflow;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    private TareaRepository tareaRepository;

    public TareaController(TareaRepository tareaRepository){
        this.tareaRepository = tareaRepository;
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
    @PostMapping//metodo para crear nueva tarea
    Tarea crear(@RequestBody Tarea tarea){
        return tareaRepository.save(tarea);
    }

    @PutMapping("/{id}")//metodo para actualizar
    Tarea actualizar(@PathVariable Integer id, @RequestBody Tarea form){
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        tarea.setTitulo(form.getTitulo());
        return tareaRepository.save(tarea);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")//metodo para eliminar tarea
    void eliminar(@PathVariable Integer id){
        tareaRepository.deleteById(id);
    }
}
