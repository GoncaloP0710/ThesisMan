package pt.ul.fc.css.example.demo.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Juri;

public interface JuriRepository extends JpaRepository<Juri,Integer> {}
