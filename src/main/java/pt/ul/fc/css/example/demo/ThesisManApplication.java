
package pt.ul.fc.css.example.demo;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.*;



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
                                    CandidaturaRepository repository6,
                                    MestradoRepository repository7,
                                    JuriRepository repository8,
                                    DefesaRepository repository9){
        return (args) -> {

            //------------------------Criar mestrados----------------------------------------------//
            Mestrado mestrado1 = new Mestrado("MEIC-A");
            Mestrado mestrado2 = new Mestrado("Yappin");

            
            repository7.save(mestrado1);
            repository7.save(mestrado2);


            //------------------------Criar alunos----------------------------------------------//
            Aluno aluno1 = new Aluno(12.2, "João", "12341", mestrado2);
            Aluno aluno2 = new Aluno(16.5, "Maria", "12342", mestrado1);
            Aluno aluno3 = new Aluno(14.3, "Manuel", "12343", mestrado1);
            Aluno aluno4 = new Aluno(10.0, "Ana", "12344", mestrado2);
            Aluno aluno5 = new Aluno(9.5, "Rita", "12345", mestrado2);

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

            mestrado1.setCoordenador(docente1);
            mestrado2.setCoordenador(docente3);
            repository7.save(mestrado1);
            repository7.save(mestrado2);
    
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

            tema1.addMestradosCompativeis(mestrado2);
            //repository4.save(tema1);
            repository7.save(mestrado2);

            tema2.addMestradosCompativeis(mestrado2);
            repository4.save(tema2);
            repository7.save(mestrado2);

            tema1.addMestradosCompativeis(mestrado1);
            repository4.save(tema1); // !
            repository7.save(mestrado1);

           //----------------------------Criar Candidaturas---------------------------------------------//
           Candidatura candidatura1 = new Candidatura(new Date(), EstadoCandidatura.APROVADO, aluno1);
           Candidatura candidatura2 = new Candidatura(new Date(), EstadoCandidatura.EMPROCESSAMENTO, aluno2);
           candidatura1.setTema(tema1);
           repository6.save(candidatura1);
           candidatura2.setTema(tema2);
           repository6.save(candidatura2);



           //----------------------------Criar Teses---------------------------------------------//
            Dissertacao tese1 = new Dissertacao(candidatura1);
            Projeto tese2 = new Projeto(candidatura2);
            repository5.save(tese1);
            repository5.save(tese2);

            tese2.setOrientadorInterno(docente1);
            repository5.save(tese2);

            //----------------------------Criar Defesas---------------------------------------------//
            Defesa defesa1 = new Defesa(false, false);
            Defesa defesa2 = new Defesa(false, true);
            repository9.save(defesa1);
            repository9.save(defesa2);

            defesa1.setTese(tese1);
            repository9.save(defesa1);
            defesa2.setTese(tese2);
            repository9.save(defesa2);

            defesa1.setDate(new Date());
            repository9.save(defesa1);
            defesa2.setDate(new Date());
            repository9.save(defesa2);

            defesa1.setSala("1.2.24");
            repository9.save(defesa1);
            defesa2.setSala("1.3.30");
            repository9.save(defesa2);

            //----------------------------Criar Juris---------------------------------------------//
            Juri juri1 = new Juri(docente3, docente4);
            defesa1.setJuri(juri1);
            repository8.save(juri1);
            repository9.save(defesa1);

            

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
