package br.com.pizzaria.uniamerica.controller;

import br.com.pizzaria.uniamerica.dto.pizzaDTOs.PizzaDTO;
import br.com.pizzaria.uniamerica.entities.Pizza;
import br.com.pizzaria.uniamerica.service.PizzaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public ResponseEntity<?> buscaPeloId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(this.pizzaService.findById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lista-todos")
    public ResponseEntity<?> listaTodos(){
        try {
            return ResponseEntity.ok(this.pizzaService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping @Transactional
    public ResponseEntity<?> cadastra(@RequestBody PizzaDTO pizzaDTO){
        try {
            return ResponseEntity.ok(this.pizzaService.cadastra(pizzaDTO));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/altera") @Transactional
    public ResponseEntity<?> altera(@RequestBody Pizza pizza){
        try {
            return ResponseEntity.ok(this.pizzaService.altera(pizza));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativa") @Transactional
    public ResponseEntity<?> desativa(@RequestParam Long id){
        try {
            this.pizzaService.desativa(id);
            return ResponseEntity.ok("ID desativado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
