package pc.todotic.taskflow.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pc.todotic.taskflow.modelo.Tarea;

public interface TareaRepository extends JpaRepository<Tarea,Integer> {
}
