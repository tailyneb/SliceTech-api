package br.com.pizzaria.uniamerica.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tb_produto", schema = "public")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Produto extends AbstractEntity {
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "EstoqueProd_id")
    private EstoqueProduto produto;
    @Getter @Setter
    @Column(name = "quantidade")
    private int quantidade;
    @Getter @Setter
    @Column(name = "valor_total")
    private double valorTotalProduto;
}