package pt.ul.fc.css.example.demo.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.example.demo.entities.Tese;

@Repository
public interface TeseRepository extends JpaRepository<Tese, Integer> {
    Optional<Tese> findById(Integer id);
}