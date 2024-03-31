
package pt.ul.fc.css.example.demo;
import java.time.LocalDate;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.EstadoCandidatura;
import pt.ul.fc.css.example.demo.entities.Projeto;
import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.repositories.UtilizadorEmpresarialRepository;



@SpringBootApplication
public class ThesisManApplication {

    private static final Logger log = LoggerFactory.getLogger(ThesisManApplication.class);

    public static void main(String[] args){
        SpringApplication.run(ThesisManApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AlunoRepository repository, DocenteRepository repository2, 
                                    UtilizadorEmpresarialRepository repository3,
                                    TemaRepository repository4,
                                    TeseRepository repository5,
                                    CandidaturaRepository repository6){
        return (args) -> {


            //------------------------Criar alunos----------------------------------------------//
            Aluno aluno1 = new Aluno(12.2, "João", "12341");
            Aluno aluno2 = new Aluno(16.5, "Maria", "12342");
            Aluno aluno3 = new Aluno(14.3, "Manuel", "12343");
            Aluno aluno4 = new Aluno(10.0, "Ana", "12344");
            Aluno aluno5 = new Aluno(9.5, "Rita", "12345");

            repository.save(aluno1);
            repository.save(aluno2);
            repository.save(aluno3);
            repository.save(aluno4);
            repository.save(aluno5);



            //---------------------------Criar docentes-----------------------------------------//
            Docente docente1 = new Docente("informática", true, "Alcides", "1234");
            Docente docente2 = new Docente("biologia", false, "José", "1235");
            Docente docente3 = new Docente("matemática", false, "Fiorentino", "1236");
            Docente docente4 = new Docente("física", true, "Felipe", "1237");

            repository2.save(docente1);
            repository2.save(docente2);
            repository2.save(docente3);
            repository2.save(docente4);

    
            //-------------------------Criar Utilizadores Empresariais--------------------------//
            UtilizadorEmpresarial utilizadorEmpresarial1 = new UtilizadorEmpresarial("PainConjunta", "João", "969 833 441");
            UtilizadorEmpresarial utilizadorEmpresarial2 = new UtilizadorEmpresarial("PainConjunta", "Xiting Wang", "920 186 037");
            UtilizadorEmpresarial utilizadorEmpresarial3 = new UtilizadorEmpresarial("PainConjunta", "João", "965 369 829");

            repository3.save(utilizadorEmpresarial1);
            repository3.save(utilizadorEmpresarial2);
            repository3.save(utilizadorEmpresarial3);

            
            
            //----------------------------Criar e Submeter Temas---------------------------------------------//
            Tema tema1 = new Tema("Femboy Hooters", "Femboys", 750.0f, docente1);
            Tema tema2 = new Tema("Brawl Stars Art of war", "yaping", 69.0f, docente4);

            repository4.save(tema1);
            repository4.save(tema2);

            

           //----------------------------Criar Candidaturas---------------------------------------------//
           //@NonNull Date dataCandidatura, @NonNull EstadoCandidatura estado
           Candidatura candidatura1 = new Candidatura(new Date(), EstadoCandidatura.EMPROCESSAMENTO);
           candidatura1.setTema(tema1);
           repository6.save(candidatura1);
           aluno1.setCandidatura(candidatura1);
           repository6.save(candidatura1);
           repository.save(aluno1);
           
           repository6.save(candidatura1);

           //----------------------------Criar Teses---------------------------------------------//
            Projeto projeto1 = new Projeto(candidatura1);
            candidatura1.setTese(projeto1);

            repository5.save(projeto1);
            repository6.save(candidatura1);

            projeto1.setOrientadorInterno(docente1);
            repository5.save(projeto1);
            repository2.save(docente1);
            repository6.save(candidatura1);


            // ----------------------------------------------------------------------------------//
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
