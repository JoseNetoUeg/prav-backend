package com.prav.atividade01.prav_backend_servico.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemDto {
    private Long id;
    private String referencia;
    private Long clienteId;
    private LocalDateTime dataCriacao;
    private String status;
    private BigDecimal total;
    private List<OrdemItemDto> itens = new ArrayList<>();

    public OrdemDto() {}

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

    public List<OrdemItemDto> getItens() { return itens; }
    public void setItens(List<OrdemItemDto> itens) { this.itens = itens; }
}
