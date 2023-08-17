package br.com.pizzaria.uniamerica.service;

import br.com.pizzaria.uniamerica.dto.pizzaDTOs.PizzaDTO;
import br.com.pizzaria.uniamerica.entities.Pizza;
import br.com.pizzaria.uniamerica.repository.PizzaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    public PizzaDTO findById(Long id){
        Pizza pizza = this.pizzaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("O ID informado não foi encontrado!"));
        PizzaDTO pizzaDTO = new PizzaDTO(pizza);
        return pizzaDTO;
    }

    public List<Pizza> findAll(){
        List<Pizza> pizzaList = this.pizzaRepository.findAll();
        return pizzaList;
    }

    @Transactional
    public PizzaDTO cadastra(PizzaDTO pizzaDTO){
        Pizza pizza = new Pizza(pizzaDTO);
        this.pizzaRepository.save(pizza);
        return pizzaDTO;
    }

    @Transactional
    public PizzaDTO altera(Pizza pizza){
        Pizza pizza1 = this.pizzaRepository.findById(pizza.getId())
                .orElseThrow(()-> new RuntimeException("O ID informado não foi encontrado!"));

        pizza1.setSabor(pizza.getSabor());
        pizza1.setTamanhoPizza(pizza.getTamanhoPizza());
        pizza1.setValor(pizza.getValor());
        pizza1.setDescricao(pizza.getDescricao());

        this.pizzaRepository.save(pizza1);
        PizzaDTO pizzaDTO = new PizzaDTO(pizza1);
        return pizzaDTO;
    }

    @Transactional
    public void desativa(Long id){
        Pizza pizza = this.pizzaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("O ID informado não foi encontrado!"));
        pizza.setAtivo(false);
        this.pizzaRepository.save(pizza);
    }
}
