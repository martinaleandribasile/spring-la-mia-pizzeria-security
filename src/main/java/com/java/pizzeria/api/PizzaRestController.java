package com.java.pizzeria.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.pizzeria.model.Pizza;
import com.java.pizzeria.repository.PizzaRepository;



@RestController
@CrossOrigin
@RequestMapping("/api/pizze")
public class PizzaRestController {
	@Autowired
	PizzaRepository pizzaRepository;
	
	@GetMapping()
	public List<Pizza> index(){
		return pizzaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pizza> show(@PathVariable("id") Integer id){
		Optional<Pizza> pizza= pizzaRepository.findById(id); 
		if(pizza.isEmpty()) {
			return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Pizza>(pizza.get(),HttpStatus.OK);
			
		}
	}
	@PostMapping("/create")
	public Pizza create(@RequestBody Pizza pizza) {
		return pizzaRepository.save(pizza);
	}
	
	@GetMapping("/delete/{id}")
	public boolean deletePizza(@PathVariable("id") int id) {
		try {
			pizzaRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	@GetMapping("/search/{query}")
	public List<Pizza> getSearchPizza(@PathVariable("query") String query) {
		
		List<Pizza> queryPizza = query == null ? pizzaRepository.findAll() : pizzaRepository.findByNomeLike(query);
		return queryPizza ;
	}
	
	@PutMapping("/edit/{id}")
	public Pizza edit(@RequestBody Pizza pizza, @PathVariable(name = "id") Integer id) {
		Pizza pizzaEdit = pizzaRepository.getReferenceById(id);
		pizzaEdit.setId(id);
		pizzaEdit.setNome(pizza.getNome());
		pizzaEdit.setFoto(pizza.getFoto());
		pizzaEdit.setPrezzo(pizza.getPrezzo());

		return pizzaRepository.save(pizzaEdit);
	}
	
}
