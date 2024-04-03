
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
            Mestrado mestrado1 = new Mestrado("Engenharia Informática");
            Mestrado mestrado2 = new Mestrado("Engenharia Biológica");
            Mestrado mestrado3 = new Mestrado("Matemática Aplicada");
            Mestrado mestrado4 = new Mestrado("Engenharia Física Tecnológica");

            
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
            Docente docente1 = new Docente("Informática", true, "Joaquim", "1234");
            Docente docente2 = new Docente("Biologia", false, "José", "1235");
            Docente docente3 = new Docente("Matemática", false, "Duarte", "1236");
            Docente docente4 = new Docente("Física", true, "Felipe", "1237");

            repository2.save(docente1);
            repository2.save(docente2);
            repository2.save(docente3);
            repository2.save(docente4);

            mestrado1.setCoordenador(docente1);
            mestrado2.setCoordenador(docente2);
            mestrado2.setCoordenador(docente3);
            mestrado2.setCoordenador(docente4);

            repository7.save(mestrado1);
            repository7.save(mestrado2);
            repository7.save(mestrado3);
            repository7.save(mestrado4);

    
            //-------------------------Criar Utilizadores Empresariais--------------------------//
            UtilizadorEmpresarial utilizadorEmpresarial1 = new UtilizadorEmpresarial("Empresa de Informática", "João", "345-3231-677");
            UtilizadorEmpresarial utilizadorEmpresarial2 = new UtilizadorEmpresarial("Empresa de Matemática", "Wang", "345-3331-697");
            UtilizadorEmpresarial utilizadorEmpresarial3 = new UtilizadorEmpresarial("Empresa de Biologia", "Fernando", "325-3131-695");

            repository3.save(utilizadorEmpresarial1);
            repository3.save(utilizadorEmpresarial2);
            repository3.save(utilizadorEmpresarial3);

            
            
            //----------------------------Criar e Submeter Temas---------------------------------------------//
            Tema tema1 = new Tema("Videogame Accesiblity", "How to create games that are inclusive to every kind of person", 750.0f, docente1);
            Tema tema2 = new Tema("Descrição de Algoritmos super mega potentes", "Com o auxílio da Matemática, descrever algoritmos capazes de fazer este projeto melhor que nós", 69.0f, utilizadorEmpresarial2);
            Tema tema3 = new Tema("Baleias", "Baleias são mamíferos tão inteligentes que precisamos de saber mais sobre eles", 43.0f, docente2);
            repository4.save(tema1);
            repository4.save(tema2);
            repository4.save(tema3);

            tema1.addMestradosCompativeis(mestrado1);
            
            repository7.save(mestrado1);

            tema2.addMestradosCompativeis(mestrado3);
            tema2.addMestradosCompativeis(mestrado1);
            repository4.save(tema2);
            repository7.save(mestrado1);
            repository7.save(mestrado3);


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

            defesa1.setData(new Date());
            repository9.save(defesa1);
            defesa2.setData(new Date());
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
