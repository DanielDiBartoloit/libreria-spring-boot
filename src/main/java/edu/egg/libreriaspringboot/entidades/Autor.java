package edu.egg.libreriaspringboot.entidades;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.util.List;


@Entity
@Getter @Setter // @NoArgsConstructor // @AllArgsConstructor // usar solo getters setters // @data g s tostring re//
@SQLDelete(sql = "UPDATE Autor a SET a.alta = false WHERE a.id = ?") // sql puro
@Where(clause = "alta = true") // condicion//
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;
}





