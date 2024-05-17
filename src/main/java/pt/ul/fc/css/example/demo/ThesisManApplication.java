
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
    public CommandLineRunner demo(){
        return (args) -> {};

    }
}