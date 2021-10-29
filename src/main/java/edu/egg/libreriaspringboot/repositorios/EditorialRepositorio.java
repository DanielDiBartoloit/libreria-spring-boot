package edu.egg.libreriaspringboot.repositorios;


import edu.egg.libreriaspringboot.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial,Integer> {

    @Query(value="SELECT e FROM Editorial e WHERE e.nombre LIKE %:keyword%")
    List<Editorial> buscarPornombreEnBD(@Param("keyword") String keyword);

    @Modifying
    @Query("UPDATE Editorial e SET e.nombre = :nombre WHERE e.id = :id")
    void modificarNombreEditorial(@Param("id") Integer id,@Param("nombre") String nombre);

    @Modifying
    @Query("UPDATE Autor a SET a.alta = true WHERE a.id = :id")
    void habilitarEditorial(@Param("id") Integer id);
}
