package com.practica.TaskManager.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.practica.TaskManager.entities.User;
import com.practica.TaskManager.response.ResponseMessage;
import com.practica.TaskManager.services.UserService;

@RestController
@RequestMapping("/api/TK")
public class UserController {
	
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService service;

	@PostMapping("/user/create")
	public ResponseEntity<?> save(@RequestBody User newUser) {
		try {
			if(newUser.getId()!=null) {
				log.warn("trying to create a User with id");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("trying to create a book with id"));			
			}
			else {
				User savedUser = service.save(newUser);
				return ResponseEntity.created(new URI("/user/"+savedUser.getId())).body(savedUser);
			}	
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();		}
	}
	
	@GetMapping("/users")
	public ResponseEntity<?>  getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		try {
			if(!service.existsById(id)) {
				log.warn("not found id");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("not found id"));
			}
			return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PutMapping("/user/update/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody User user){
		try {
			if(!service.existsById(id)) {
				log.warn("Trying to update a non existent user");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Trying to update a non existent user"));
			}
			return ResponseEntity.status(HttpStatus.OK).body(service.update(id, user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("User not Update."));		
			}
	}
	
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<?> deleleteByID(@PathVariable Integer id){
		try {
			if(!service.existsById(id)) {
				log.warn("not found id");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("not found id"));
			}
			service.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage("User deleted"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Error to found User or Don´t exist."));
		}
	}
	
	@DeleteMapping("/user/delete")
	public ResponseEntity<?> deleteByUser(@RequestBody User user){
		try {
			service.delete(user);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
}
