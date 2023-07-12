package pc.todotic.taskflow.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pc.todotic.taskflow.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
}
