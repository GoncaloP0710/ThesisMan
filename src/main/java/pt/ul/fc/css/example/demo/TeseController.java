package pt.ul.fc.css.example.demo;


import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;

@RestController
@RequestMapping("/api")
public class TeseController {
    private final TeseRepository teseRepository;

    public TeseController(TeseRepository teseRepository) {
        this.teseRepository = teseRepository;
    }

    @GetMapping("/tese/{id}")
    public Tese getTese(@PathVariable Integer id) {
        return this.teseRepository.getReferenceById(id);
    }

    @PutMapping("/tese")
    public Integer putTese(@NonNull @RequestBody Tese tese) {
        Tese saved = teseRepository.save(tese);
        return saved.getId();
    }

    @RequestMapping(value = "/index")
    public ModelAndView home() {
        return new ModelAndView("index");
    }
}
