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

import com.java.pizzeria.model.Ingredienti;
import com.java.pizzeria.model.Pizza;
import com.java.pizzeria.repository.IngredientiRepository;
import com.java.pizzeria.repository.PizzaRepository;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/ingredienti") 
public class IngredienteController {
	
	@Autowired IngredientiRepository ingredientiRepository;
	@Autowired
	PizzaRepository pizzaRepository;
	@GetMapping()		
	public String index(Model model) {	
		List<Ingredienti> ing = ingredientiRepository.findAll();	
		model.addAttribute("ingredienti", ing);
		return "ingredienti/index";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Ingredienti ingrediente=new Ingredienti();
		model.addAttribute("ingrediente", ingrediente);
		return "ingredienti/create";
		
	}
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("ingrediente") Ingredienti formIng, 
			BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors())
			return "ingredienti/create";
		
		ingredientiRepository.save(formIng);
		return "redirect:/ingredienti";
		
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Optional<Ingredienti> ing= ingredientiRepository.findById(id);
		if(ing.isEmpty()) {
			return "redirect:/error";
		}
		model.addAttribute("ing",ing.get());
		return "ingredienti/edit";
	}
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ingrediente") Ingredienti formIng, 
			BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors())
			return "ingredienti/create";
		
		ingredientiRepository.save(formIng);
		return "redirect:/ingredienti";
		
	}
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		Ingredienti ingredienti=ingredientiRepository.findById(id).orElseThrow();
		ingredienti.getPizza().forEach(p -> p.getIngredienti().remove(ingredienti));
		pizzaRepository.saveAll(ingredienti.getPizza());
		ingredientiRepository.deleteById(id);
		return "redirect:/ingredienti";
	}
}
