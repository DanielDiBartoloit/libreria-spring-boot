package edu.egg.libreriaspringboot.repository;

import edu.egg.libreriaspringboot.entity.Autor;
import edu.egg.libreriaspringboot.entity.Editorial;
import edu.egg.libreriaspringboot.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer> {

    @Modifying
    @Query("UPDATE Libro l SET l.alta = true WHERE l.id = :id")
    void habilitarLibro(@Param("id") Integer id);

    @Query(value="SELECT l FROM Libro l WHERE l.titulo LIKE %:keyword%")
    List<Libro> buscarPorTituloEnBD(@Param("keyword") String keyword);

    @Modifying
    @Query("UPDATE Libro l SET l.isbn = :isbn, l.titulo = :titulo, l.anio = :anio, l.ejemplares = :ejemplares, l.ejemplaresPrestados = :ejemplaresPrestados, l.autor = :autor, l.editorial = :editorial WHERE l.id = :id")
    void modificarLibro(@Param("id") Integer id, @Param("isbn") Long isbn,@Param("titulo") String titulo, @Param("anio") Integer anio, @Param("ejemplares") Integer ejemplares, @Param("ejemplaresPrestados") Integer ejemplaresPrestados, @Param("autor") Autor autor, @Param("editorial") Editorial editorial);

    @Query(value="SELECT l FROM Libro l WHERE l.isbn = :isbn")
    Optional<Libro> buscarLibroPorIsbn(@Param("isbn") Long isbn);

    boolean existsByIsbn(Long isbn);
}
