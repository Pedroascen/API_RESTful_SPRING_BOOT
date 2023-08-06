package pc.todotic.taskflow.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Size(min = 5, max = 25)
    private String nombre;

    @NotNull
    @Email
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(min = 6)
    private String clave;
    @Enumerated(EnumType.STRING)
    private Estado rol;
    @Column(name = "fecha_crea")
    private LocalDateTime fechaCreacion;

    @JsonIgnore
    @OneToMany(mappedBy = "responsable",fetch = FetchType.LAZY)
    private List<Tarea> tarea = new ArrayList<>();

    public enum Estado{
        ADMIN,
        NORMAL
    }
}
