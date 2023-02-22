package com.java.pizzeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.pizzeria.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
	public List<Pizza> findByNomeLike(String nomePizza); //senza like la stringa deve combaciare
	public List<Pizza> findByDescrizioneLike(String descrizionePizza);
}
