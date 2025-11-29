package com.prav.atividade01.prav_backend_servico.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordem_item")
public class OrdemItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id", nullable = false)
    @JsonBackReference
    private Ordem ordem;

    @Column(name = "linha_num")
    private Integer linhaNum;

    // agora referencia direta para a entidade Servico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id")
    private Servico servico;

    private String descricao;

    private BigDecimal quantidade = BigDecimal.ZERO;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario = BigDecimal.ZERO;

    private BigDecimal subtotal = BigDecimal.ZERO;

    public OrdemItem() {}

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Ordem getOrdem() { return ordem; }
    public void setOrdem(Ordem ordem) { this.ordem = ordem; }

    public Integer getLinhaNum() { return linhaNum; }
    public void setLinhaNum(Integer linhaNum) { this.linhaNum = linhaNum; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
