package com.prav.atividade01.prav_backend_servico.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="servico")
public class Servico {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(name="tipoServico")
	private String tipoServico;
	
	@Column(name="descricaoDetalhada")
	private String descricaoDetalhada;
	
	@Column(name="dataExecucao")
	private Date dataExecucao;
	
	@Column(name="valor")
	private double valor;
	
	@Column(name="disponivel")
	private boolean disponivel;
	
		
	//Construtor padrao, para a super classe
	public Servico() {
		super();

	}


	//Construtor com todos os campos
	public Servico(Long codigo, String tipoServico, String descricaoDetalhada, Date dataExecucao, double valor,
			boolean disponivel) {
		super();
		this.codigo = codigo;
		this.tipoServico = tipoServico;
		this.descricaoDetalhada = descricaoDetalhada;
		this.dataExecucao = dataExecucao;
		this.valor = valor;
		this.disponivel = disponivel;
		
	}


	
	//Gets and Sets
	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public String getTipoServico() {
		return tipoServico;
	}


	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}


	public String getDescricaoDetalhada() {
		return descricaoDetalhada;
	}


	public void setDescricaoDetalhada(String descricaoDetalhada) {
		this.descricaoDetalhada = descricaoDetalhada;
	}


	public Date getDataExecucao() {
		return dataExecucao;
	}


	public void setDataExecucao(Date dataExecucao) {
		this.dataExecucao = dataExecucao;
	}


	public double getValor() {
		return valor;
	}


	public void setValor(double valor) {
		this.valor = valor;
	}


	public boolean disponivel() {
		return disponivel;
	}


	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
}