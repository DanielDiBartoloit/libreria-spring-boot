package edu.egg.libreriaspringboot.repositorios;

import edu.egg.libreriaspringboot.entidades.Autor;
import edu.egg.libreriaspringboot.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer> {

    @Modifying
    @Query("UPDATE Libro l SET l.alta = true WHERE l.id = :id")
    void habilitarLibro(@Param("id") Integer id);

    @Query(value="SELECT l FROM Libro l WHERE l.titulo LIKE %:keyword%")
    List<Libro> buscarPorTituloEnBD(@Param("keyword") String keyword);


}
