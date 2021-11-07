package edu.egg.libreriaspringboot.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter // @NoArgsConstructor // @AllArgsConstructor // usar solo getters setters // @data g s tostring re//
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Autor a SET a.alta = false WHERE a.id = ?") // sql puro
//@Where(clause = "alta = true") // condicion// sin esto soft delete y muestra, habilitado lo saca de la vista

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    private LocalDateTime fechaModificacion;

    private Boolean alta;

    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;
}








