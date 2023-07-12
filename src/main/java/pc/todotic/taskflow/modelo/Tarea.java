package pc.todotic.taskflow.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descripcion;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private Integer prioridad;
    @Column(name = "fecha_crea")
    private LocalDateTime fechaCreacion;
    private LocalDate fechaLimite;
    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "id")
    private Usuario responsable_id;
    public enum Estado {
        CREADO, // = 0
        ASIGNADO, // 1
        FINALIZADO // 2
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Usuario getResponsable_id() {
        return responsable_id;
    }

    public void setResponsable_id(Usuario responsable_id) {
        this.responsable_id = responsable_id;
    }
}
