package br.com.pizzaria.uniamerica.controller;

import br.com.pizzaria.uniamerica.dto.enderecoDTOs.EnderecoDTO;
import br.com.pizzaria.uniamerica.entities.Endereco;
import br.com.pizzaria.uniamerica.service.EnderecoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<?> buscaPeloId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(this.enderecoService.findById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lista-todos")
    public ResponseEntity<?> buscaTodos(){
        try {
            return ResponseEntity.ok(this.enderecoService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping @Transactional
    public ResponseEntity<?> cadastra(@RequestBody EnderecoDTO enderecoDTO){
        try {
            return ResponseEntity.ok(this.enderecoService.cadastra(enderecoDTO));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/altera") @Transactional
    public ResponseEntity<?> altera(@RequestBody Endereco endereco){
        try {
            return ResponseEntity.ok(this.enderecoService.altera(endereco));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativa/{id}") @Transactional
    public ResponseEntity<?> desativa(@PathVariable Long id){
        try {
            this.enderecoService.desativa(id);
            return ResponseEntity.ok("Endereço desativado com sucesso!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}