
package pt.ul.fc.css.example.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;



@SpringBootApplication
public class ThesisManApplication {

    private static final Logger log = LoggerFactory.getLogger(ThesisManApplication.class);

    public static void main(String[] args){
        SpringApplication.run(ThesisManApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AlunoRepository repository){
        return (args) -> {
            repository.save(new Aluno(12.2, null, "João", "12341"));

            //fectch aluno
            log.info("Alunos encontrados com findAll():");
            log.info("-------------------------------");
            for(Aluno aluno : repository.findAll()){
                log.info(aluno.toString());
            }
            log.info("");
        };
    }
}
