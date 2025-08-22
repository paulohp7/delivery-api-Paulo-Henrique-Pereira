package com.deliverytech.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
// import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String categoria;

    private String endereco;

    private String telefone;

    private BigDecimal taxaEntrega;

    private BigDecimal avaliacao;

    private Integer tempoEntregaMinutos;

    @Builder.Default
    private Boolean ativo = true;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    @OneToMany(mappedBy = "restaurante")
    private List<Pedido> pedidos;
}
