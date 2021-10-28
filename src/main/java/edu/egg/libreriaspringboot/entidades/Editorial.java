package edu.egg.libreriaspringboot.entidades;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@SQLDelete(sql = "UPDATE Editorial e SET e.alta = false WHERE e.id = ?")
@Where(clause = "alta = true")
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "editorial")
    private List<Libro> libros;


}




