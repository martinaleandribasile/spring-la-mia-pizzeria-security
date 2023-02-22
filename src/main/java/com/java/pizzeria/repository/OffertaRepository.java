package com.java.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.pizzeria.model.Offerta;

public interface OffertaRepository extends JpaRepository<Offerta, Integer> {

}
