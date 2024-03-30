package pt.ul.fc.css.example.demo;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.repositories.DefesaRepository;

@RestController
@RequestMapping("/api")
public class DefesaController {

    private final DefesaRepository defesaRepository;

    public DefesaController(DefesaRepository defesaRepository){
        this.defesaRepository = defesaRepository;
    }
    
    @GetMapping("/defesa/{id}")
    public Defesa getDefesa(@PathVariable Integer id) {
        return this.defesaRepository.getReferenceById(id);
    }

    @PutMapping("/defesa")
    public Integer putDefesa(@NonNull @RequestBody Defesa defesa) {
        Defesa saved = defesaRepository.save(defesa);
        return saved.getId();
    }

    @RequestMapping(value = "/index")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

}
