package com.example.comapp.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Cliente implements Serializable{
	
	
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		private String nome;
		private String cpf;
		private int numVoo;
		private String tipoVoo;//nacional ou internacional
		private int altura;
		private int largura;
		private int comprimento;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getCpf() {
			return cpf;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
		public int getNumVoo() {
			return numVoo;
		}
		public void setNumVoo(int numVoo) {
			this.numVoo = numVoo;
		}
		public String getTipoVoo() {
			return tipoVoo;
		}
		public void setTipoVoo(String tipoVoo) {
			this.tipoVoo = tipoVoo;
		}
		public int getAltura() {
			return altura;
		}
		public void setAltura(int altura) {
			this.altura = altura;
		}
		public int getLargura() {
			return largura;
		}
		public void setLargura(int largura) {
			this.largura = largura;
		}
		public int getComprimento() {
			return comprimento;
		}
		public void setComprimento(int comprimento) {
			this.comprimento = comprimento;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
}
