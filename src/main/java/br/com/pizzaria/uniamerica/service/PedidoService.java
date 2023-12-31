package br.com.pizzaria.uniamerica.service;

import br.com.pizzaria.uniamerica.dto.clienteDTOs.ClienteDTO;
import br.com.pizzaria.uniamerica.dto.pedidoDTOs.PedidoDTO;
import br.com.pizzaria.uniamerica.entities.*;
import br.com.pizzaria.uniamerica.repository.ClienteRepository;
import br.com.pizzaria.uniamerica.repository.PedidosRepository;
import br.com.pizzaria.uniamerica.repository.PizzaRepository;
import br.com.pizzaria.uniamerica.service.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public Pedido findById(Long id){
        Pedido pedido = pedidosRepository.findById(id).orElseThrow( () -> new RuntimeException("Recurso não encontrado!"));
        return  pedido;
    }

    @Transactional(readOnly = true)
    public List<Pedido> findAll(){
        List<Pedido> lista = pedidosRepository.findAll();
        List<Pedido> listaAtivos = pedidosRepository.findAll();
        for (Pedido ped: lista) {
            if (ped.isAtivo()){
                listaAtivos.add(ped);
            }
        }
        return listaAtivos;
    }

    @Transactional
    public Pedido insert(PedidoDTO pedidoDTO){
        var pizza = pizzaRepository.findById(pedidoDTO.getPizzaId()).orElseThrow(null);
        var cliente = clienteRepository.findById(pedidoDTO.getClientId()).orElseThrow(null);

        Pedido pedido = new Pedido(cliente,pizza,pedidoDTO.getFormaPagamentoId(),pedidoDTO.getDescricao(),pedidoDTO.getValor(),pedidoDTO.isEntrega(),pedidoDTO.isSituacao());
        this.pedidosRepository.save(pedido);
        return  pedido;
    }

    @Transactional
    public PedidoDTO update(Long id,PedidoDTO pedidoDTO){
        try {
            Pedido pedido = pedidosRepository.getReferenceById(id);
//            copyDtoToEntity(pedidoDTO, pedido);
            pedido = pedidosRepository.save(pedido);
            return new PedidoDTO(pedido);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }

    @Transactional
    public void statusCancelado(Long id){
        this.pedidosRepository.statusCancelado(id);
    }

    private Pedido copyDtoToEntity(PedidoDTO pedidoDTO, Pizza pizza) {
        Pedido pedido = new Pedido();
        Cliente cliente = new Cliente();

        pedido.setDescricao(pedidoDTO.getDescricao());
        pedido.setValor(pedidoDTO.getValor());
        pedido.setEntrega(pedidoDTO.isEntrega());
        pedido.setSituacao(pedidoDTO.isSituacao());
        pedido.setCliente(cliente);
        pedido.setPizza(pizza);

        return pedido;
    }

    @Transactional
    public void statusSituacao(Long id){
        this.pedidosRepository.statusEncerrado(id);
    }
}
