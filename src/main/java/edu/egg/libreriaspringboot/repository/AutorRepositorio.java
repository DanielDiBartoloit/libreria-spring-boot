package edu.egg.libreriaspringboot.repository;

import edu.egg.libreriaspringboot.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor,Integer> {

    @Query(value="SELECT a FROM Autor a WHERE a.nombre LIKE %:keyword%")
    List<Autor> buscarPornombreEnBD(@Param("keyword") String keyword);

    @Modifying
    @Query("UPDATE Autor a SET a.nombre = :nombre WHERE a.id = :id")
    void modificarNombreAutor(@Param("id") Integer id,@Param("nombre") String nombre);

    @Modifying
    @Query("UPDATE Autor a SET a.alta = true WHERE a.id = :id")
    void habilitarAutor(@Param("id") Integer id);

}













