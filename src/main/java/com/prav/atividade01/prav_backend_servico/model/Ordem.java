package com.prav.atividade01.prav_backend_servico.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordem")
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referencia;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    private String status;

    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrdemItem> itens = new ArrayList<>();

    public Ordem() {}

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<OrdemItem> getItens() { return itens; }
    public void setItens(List<OrdemItem> itens) { this.itens = itens; }

    // helpers para manter consistência da relação
    public void addItem(OrdemItem item) {
        item.setOrdem(this);
        this.itens.add(item);
    }

    public void removeItem(OrdemItem item) {
        item.setOrdem(null);
        this.itens.remove(item);
    }
}
