package edu.egg.libreriaspringboot.entidades;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@SQLDelete(sql = "UPDATE Libro l SET l.alta = false WHERE l.id = ?")
@EntityListeners(AuditingEntityListener.class)
//@Where(clause = "alta = true")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Long isbn;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private Integer ejemplares;

    @Column(nullable = false)
    private Integer ejemplaresPrestados;

    private Integer ejemplaresRestantes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    private LocalDateTime fechaModificacion;

    private Boolean alta;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Editorial editorial;
}






