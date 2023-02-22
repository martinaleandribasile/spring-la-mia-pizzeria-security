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

import com.java.pizzeria.model.Pizza;
import com.java.pizzeria.repository.PizzaRepository;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/pizze")  
public class PizzaController {
	@Autowired
	PizzaRepository pizzaRepository;
	
	@GetMapping
	public String index(@RequestParam(name="keyword", required= false) String keyword,  Model model) {
		//List<Pizza> elencoPizze= pizzaRepository.findByNomeLike("%ghe%"); ricerca in base alla stringa che inserisco come parametro di ricerca
		List<Pizza> elencoPizze;
		boolean ricerca = false;
		String valueInput="";
		if(keyword!=null && !keyword.isEmpty()) {
			elencoPizze= pizzaRepository.findByNomeLike("%"+ keyword +"%");
			 ricerca=true;
			 valueInput=keyword;
		}else {
			elencoPizze= pizzaRepository.findAll();
		}
		model.addAttribute("elencoPizze" , elencoPizze);
		model.addAttribute("ricerca", ricerca);
		model.addAttribute("value", valueInput);
		return "pizze/indexPizze";
	}
	
	@GetMapping("{id}") //richieste get /pizze/{id}
	public String dettailsPizza(@PathVariable("id") Integer id, Model model) {
		Optional<Pizza> p= pizzaRepository.findById(id);
		if(p.isEmpty()) {
			
		}
		model.addAttribute("pizza",p.get());
		return "pizze/dettaglioPizza";
	}
	@GetMapping("/newPizza")
	public String create(Model model) {
		Pizza pizza=new Pizza();
		model.addAttribute("pizza", pizza);
		return "pizze/newPizza";
	}
	
	@PostMapping("/newPizza")
	public String store(@Valid @ModelAttribute("pizza") Pizza formPizza,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "pizze/newPizza";
		}
		pizzaRepository.save(formPizza);
		return "redirect:/pizze";
		
	}
	
	@GetMapping("/editPizza/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Optional<Pizza> p= pizzaRepository.findById(id);
		if(p.isEmpty()) {
			
		}
		model.addAttribute("pizza",p.get());
		return "pizze/editPizza";
	}
	@PostMapping("/editPizza/{id}")
	public String update( @PathVariable("id") Integer id,@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "pizze/editPizza";
		}
		pizzaRepository.save(formPizza);
		return "redirect:/pizze/"+ id;
	}
	
	@PostMapping("/deletePizza/{id}")
	public String delete(@PathVariable("id") Integer id) {
		pizzaRepository.deleteById(id);
		return "redirect:/pizze";
	}
}
