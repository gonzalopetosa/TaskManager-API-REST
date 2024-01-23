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

import com.practica.TaskManager.entities.Task;
import com.practica.TaskManager.entities.User;
import com.practica.TaskManager.response.ResponseMessage;
import com.practica.TaskManager.services.TaskService;
import com.practica.TaskManager.services.UserService;

@RestController
@RequestMapping("/api/TK")
public class TaskController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	TaskService taskService;
	
	
	@PostMapping("/task/{userId}")
	public ResponseEntity<?> createForUser(@PathVariable Integer userId, @RequestBody Task taskRequest){
		try {		
			if(!userService.existsById(userId)) {
				log.warn("Not found User");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Not found User"));
			}
			else {
				User user = userService.findById(userId).get();
				taskRequest.setUser(user);
				Task savedTask = taskService.save(taskRequest);
				user.getTasks().add(savedTask);
				userService.save(user);
				
				return ResponseEntity.created(new URI("/task/"+userId)).body(savedTask);
			}
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Error"));
        }	
	}
	
	
	@GetMapping("/tasks")
	public ResponseEntity<?> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
	}
	
	@GetMapping("/task/{userId}")
	public ResponseEntity<?> getByUserId(@PathVariable Integer userId){
		try {
			if(!userService.existsById(userId)) {
				log.warn("Not found User");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Not found User"));	
			}
			else
				return ResponseEntity.status(HttpStatus.OK).body(userService.findAllTaskByUserId(userId));			
		} catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Error"));
		}
	}
	
	@PutMapping("/task/{taskId}")
	public ResponseEntity<?> updateById(@PathVariable Integer taskId, @RequestBody Task task){
		try {
			if(!taskService.existsById(taskId)) {
				log.warn("Not found Task");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Not found Task"));	
			}else {
				return ResponseEntity.status(HttpStatus.OK).body(taskService.update(taskId, task));	
			}	
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Error"));
		}
	}
	
	@DeleteMapping("/task/{taskId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer taskId){
		try {
			if(!taskService.existsById(taskId)) {
				log.warn("Task ID not exist");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Task ID not exist"));	
			}else
				taskService.deleteById(taskId);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Error"));
		}
	}
	
	
}
