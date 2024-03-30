package pt.ul.fc.css.example.demo;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;


@RestController
@RequestMapping("/api")
public class TemaController {
    
    private final TemaRepository temaRepository;

    public TemaController(final TemaRepository temaRepository){
        this.temaRepository = temaRepository;
    }

    @GetMapping("/tema/{id}")
    public Tema getTema(@PathVariable Integer id) {
        return this.temaRepository.getReferenceById(id);
    }

    @PutMapping("/tema")
    public Integer putTema(@NonNull @RequestBody Tema tema) {
        Tema saved = temaRepository.save(tema);
        return saved.getId();
    }

}
