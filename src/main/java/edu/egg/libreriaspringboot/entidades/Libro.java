package edu.egg.libreriaspringboot.entidades;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;

@Entity
@Getter @Setter
@SQLDelete(sql = "UPDATE Libro l SET l.alta = false WHERE l.id = ?")
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
    private Integer ejemplaresPrestados;
    private Integer ejemplaresRestantes;
    private Boolean alta;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private Editorial editorial;

}


