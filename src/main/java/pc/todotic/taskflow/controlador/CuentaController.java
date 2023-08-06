package pc.todotic.taskflow.controlador;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pc.todotic.taskflow.modelo.Tarea;
import pc.todotic.taskflow.repositorio.TareaRepository;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cuenta")
public class CuentaController {

    private final TareaRepository tareaRepository;

    @GetMapping("/tareas")
    List<Tarea> listarTareasUsuario(Principal principal){//metodo para listar tareas del usuario actual
        //log.info("Usuario actual: {}"+principal.getName());
        return tareaRepository.findByResponsableEmail(principal.getName());
    }

    @PutMapping("/tareas/{idTarea}/finalizar")
    ResponseEntity<Tarea> finalizar(@PathVariable Integer idTarea, Principal principal){
        String emailUsuario = principal.getName();
            Tarea tarea = tareaRepository
                    .findByIdAndResponsableEmail(idTarea,emailUsuario);

            if(tarea!=null){
                tarea.setEstado(Tarea.Estado.FINALIZADO);
                tareaRepository.save(tarea);
                return new ResponseEntity<Tarea>(tarea,HttpStatus.OK);
            }else{
                return new ResponseEntity<Tarea>(HttpStatus.BAD_REQUEST);
            }
    }
}
