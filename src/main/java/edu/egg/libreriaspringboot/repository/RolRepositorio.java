package edu.egg.libreriaspringboot.repository;

import edu.egg.libreriaspringboot.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Integer> {
}
