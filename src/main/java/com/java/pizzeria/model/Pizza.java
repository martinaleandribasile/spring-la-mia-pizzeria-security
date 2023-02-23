package com.java.pizzeria.model;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table()
public class Pizza {
	@Override
	public String toString() {
		return "Pizza-> " + nome + " " + prezzo + " euro";
	}
	@Id               // INDICA LA NOSTRA CHIAVE PRIMARIA
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	@NotNull(message="Nome: campo obbligatorio")
	@NotEmpty(message = "Nome: campo obbligatorio")
	@Size(min=3, max=100, message="il nome deve avere tra i 3 e i 100 caratteri")
//	@Column(columnDefinition = "CHAR(100)")
	private String nome;
	
	@NotNull(message="Prezzo: campo obbligatorio")
	@DecimalMin(value = "0.0", inclusive = false, message="Il prezzo deve essere superiore a 0")
    @Digits(integer=3, fraction=2)
	private BigDecimal prezzo;
	private String foto;
	
	
	@OneToMany(mappedBy = "pizza",cascade = CascadeType.REMOVE)
	private List<Offerta> offerte;
	
	@ManyToMany()
	@JoinTable(name = "pizza_ingrediente")
	private List<Ingredienti> ingredienti;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo( BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Offerta> getOfferte() {
		return offerte;
	}
	public void setOfferte(List<Offerta> offerte) {
		this.offerte = offerte;
	}
	public List<Ingredienti> getIngredienti() {
		return ingredienti;
	}
	public void setIngredienti(List<Ingredienti> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
}
