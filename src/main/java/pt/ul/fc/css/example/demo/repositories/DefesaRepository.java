package pt.ul.fc.css.example.demo.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.example.demo.entities.Defesa;

@Repository
public interface DefesaRepository extends JpaRepository<Defesa, Integer>{}
