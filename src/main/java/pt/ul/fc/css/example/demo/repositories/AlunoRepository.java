package pt.ul.fc.css.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.example.demo.entities.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Aluno findByContact(String contact);
    Aluno findByNome(String nome);
    
}