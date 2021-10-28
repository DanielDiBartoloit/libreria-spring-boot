package edu.egg.libreriaspringboot.repositorios;

import edu.egg.libreriaspringboot.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer> {


}
