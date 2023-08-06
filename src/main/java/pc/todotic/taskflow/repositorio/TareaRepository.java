package pc.todotic.taskflow.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pc.todotic.taskflow.modelo.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaRepository extends JpaRepository<Tarea,Integer> {

    List<Tarea> findByResponsableEmail(String email);

    Tarea findByIdAndResponsableEmail(Integer idTarea, String email);
}
