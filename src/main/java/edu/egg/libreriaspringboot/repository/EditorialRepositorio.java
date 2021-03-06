package edu.egg.libreriaspringboot.repository;

import edu.egg.libreriaspringboot.entity.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial,Integer> {

    @Query(value="SELECT e FROM Editorial e WHERE e.nombre LIKE %:keyword%")
    List<Editorial> buscarPornombreEnBD(@Param("keyword") String keyword);

    @Modifying
    @Query("UPDATE Editorial e SET e.alta = true WHERE e.id = :id")
    void habilitarEditorial(@Param("id") Integer id);

    Optional<Editorial> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    // Anterior
    // @Query(value = "SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    // Optional<Editorial> buscarEditorialPorNombre(@Param("nombre") String nombre);

    // @Modifying
    // @Query("UPDATE Editorial e SET e.nombre = :nombre WHERE e.id = :id")
    // void modificarNombreEditorial(@Param("id") Integer id,@Param("nombre") String nombre);
}






