package com.java.pizzeria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.pizzeria.model.Offerta;
import com.java.pizzeria.model.Pizza;
import com.java.pizzeria.repository.OffertaRepository;
import com.java.pizzeria.repository.PizzaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/offerte")
public class OffertaController {
	@Autowired
	PizzaRepository pizzaRepository;
	
	@Autowired
	OffertaRepository offertaRepository;

	@GetMapping("/create")		//gestirà le richieste GET di tipo /borrowings/create?pizzaId=xxx
	public String create(
		@RequestParam(name="pizzaId", required = true) Integer pizzaId,
		Model model) throws Exception {
		
		Offerta offerta=new Offerta();	//non esiste ancora sul DB
		
		try {
			Pizza pizza = pizzaRepository.getReferenceById(pizzaId);
			offerta.setPizza(pizza);
		} catch (EntityNotFoundException e) {
			throw new Exception("Book not present. Id="+pizzaId);
		}
		
		model.addAttribute("offerta", offerta);
		
		return "offerte/create";
	}
	
	@PostMapping("/create")  
	public String store(
		@Valid @ModelAttribute("offerta") Offerta formOfferta, 
		BindingResult bindingResult,
		Model model){
		
		if (bindingResult.hasErrors())
			return "offerte/create";
		
		offertaRepository.save(formOfferta);
		
		return "redirect:/pizze/" + formOfferta.getPizza().getId(); 
		
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Optional<Offerta> off= offertaRepository.findById(id);
		if(off.isEmpty()) {
			return "redirect:/error";
		}
		List<Pizza> elencoPizze;
		elencoPizze= pizzaRepository.findAll();
		model.addAttribute("offerta",off.get());
		model.addAttribute("pizze", elencoPizze);
		return "offerte/edit";
	}
	@PostMapping("/edit/{id}")
	public String update( @PathVariable("id") Integer id,@Valid @ModelAttribute("offerta") Offerta formOfferta, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "offerte/edit";
		}
		offertaRepository.save(formOfferta);
		return "redirect:/pizze/"+ formOfferta.getPizza().getId();
	}
	@PostMapping("/deleteOfferta/{id}")
	public String delete(@PathVariable("id") Integer id) {
		Optional<Offerta> offerta= offertaRepository.findById(id);
		Pizza pizza=offerta.get().getPizza();
		offertaRepository.deleteById(id);
		return "redirect:/pizze/" + pizza.getId();
	}
}
