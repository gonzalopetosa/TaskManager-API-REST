package com.practica.TaskManager.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.practica.TaskManager.entities.Task;
import com.practica.TaskManager.entities.User;
import com.practica.TaskManager.repository.TaskRepository;
import com.practica.TaskManager.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositorTest {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private User user;
	
	private Task task;
	
	@BeforeEach
	void setup() {
		user = User.builder()
				.id(1)
				.fullName("Gonzalo Perez")
				.email("g1@gmail.com")
				.phoneNumber("2222-2222")
				.gender("male")
				.country("Argentina")
				.build();
		
		userRepository.save(user);

		task = Task.builder()
				.monthCreation("June")
				.data("test note")
				.user(user)
				.build();
		
	}
	
	@DisplayName("save task for user")
	@Test
	void saveTaskTest() {
		Task savedTask = taskRepository.save(task);
		
		assertThat(savedTask).isNotNull();
		assertThat(savedTask.getId()).isGreaterThan(0);

	}
	
	@DisplayName("save task with user not exist in data base")
	@Test
	void saveTaskNotExistUserTest() {
		User user2 = User.builder()
				.id(1)
				.fullName("Gonzalo Perez")
				.email("g1@gmail.com")
				.phoneNumber("2222-2222")
				.gender("male")
				.country("Argentina")
				.build();

		Task task2 = Task.builder()
				.monthCreation("June")
				.data("test note")
				.user(user2)
				.build();
		 assertThrows(Exception.class, () -> taskRepository.save(task2));
	}
	
	@DisplayName("get all tasks")
	@Test
	void getAllTaskTest() {
		taskRepository.save(task);
		
		List<Task> response = taskRepository.findAll();
		
		assertThat(response).isNotNull();
		assertThat(response.size()).isGreaterThan(1);
	}
	
	@DisplayName("get all tasks by id")
	@Test
	void getTaskByIdTest() {
		taskRepository.save(task);
		
		Optional<Task> response = taskRepository.findById(task.getId());
		
		assertThat(response).isNotNull();
		assertTrue(response.isPresent());
	}
	
	@DisplayName("get tasks by not exist id")
	@Test
	void getTaskByNotExistIdTest() {
		taskRepository.save(task);
		
		Optional<Task> response = taskRepository.findById(20);
		
		assertThat(response.isPresent()).isEqualTo(false);
	}
	
	@DisplayName("modify task")
	@Test
	void modifyTaskTest() {
		User user2 = User.builder()
				.id(1)
				.fullName("Gonzalo Perez")
				.email("g1@gmail.com")
				.phoneNumber("2222-2222")
				.gender("male")
				.country("Argentina")
				.build();
		Task taskSaved = taskRepository.save(task);
		taskSaved.setMonthCreation("september");
		taskSaved.setUser(user2);
		Task taskModify = taskRepository.save(taskSaved);
		
		assertThat(taskModify.getMonthCreation()).isEqualTo("september");
		assertThat(taskModify.getUser()).isEqualTo(user2);
	}
	
	@DisplayName("delete task")
	@Test
	void deleteTaskTest() {
		taskRepository.save(task);

		taskRepository.deleteById(task.getId());
		
		Optional<Task> response = taskRepository.findById(task.getId());
		
		assertFalse(response.isPresent());
	}
		
}
