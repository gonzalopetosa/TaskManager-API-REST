package com.practica.TaskManager.Service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.practica.TaskManager.entities.Task;
import com.practica.TaskManager.entities.User;
import com.practica.TaskManager.repository.TaskRepository;
import com.practica.TaskManager.repository.UserRepository;
import com.practica.TaskManager.services.TaskService;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private TaskService taskService;
	
	private Task task;
	
	private User user;
	
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
		 
		task = Task.builder()
				.id(1)
				.monthCreation("June")
				.data("test note")
				.user(user)
				.build();
	}
	
	@DisplayName("save task for user")
	@Test
	void saveTaskTest() {
		
		given(taskRepository.save(task)).willReturn(task);
		
		Task response = taskService.save(task);
		
		assertThat(response).isNotNull();
		
	}
	
	@DisplayName("get all tasks")
	@Test
	void getAllTaskTest() {
		given(taskRepository.findAll()).willReturn(List.of(task));
		
		List<Task> response = taskService.findAll();
		
		assertThat(response).isNotNull();
		assertThat(response.size()).isEqualTo(1);
	}
	
	@DisplayName("Test to return an empty list")
    @Test
    void testListarColeccionEmpleadosVacia(){
        
        given(taskRepository.findAll()).willReturn(Collections.emptyList());
    
        List<Task> response = taskService.findAll();

        assertThat(response).isEmpty();
        assertThat(response.size()).isEqualTo(0);
    }
	
	 @DisplayName("get task by id")
	 @Test
	 void getTaskByIdTest() {
		 	
	        given(taskRepository.findById(anyInt())).willReturn(Optional.of(task));
	       
	        Optional<Task> result = taskService.findById(task.getId());

	        assertThat(result.get()).isNotNull();

	        assertThat(result.get()).isEqualTo(task);
	}
	 
	 @DisplayName("modify task")
	 @Test
	 void modifyTaskTest() throws Exception {
		 Task task2 = Task.builder()
				 .monthCreation("june")
				 .data("hello world")
				 .user(user)
				 .build();
		 
		 when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
		 
		 Task modifyTask = taskService.update(task.getId(), task2);
		 
		 assertThat(modifyTask.getData()).isEqualTo("hello world");
	 }
	 
	 @DisplayName("update task with non-existing id")
	 @Test
	 void updateTaskWithNonExistingIdTest() {
		 
		 Task task2 = Task.builder()
				 .monthCreation("june")
				 .data("hello world")
				 .user(user)
				 .build();

	        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

	        assertThrows(Exception.class, () -> taskService.update(task.getId(), task2));

	        verify(taskRepository, never()).save(any());
	    }
	
	 @DisplayName("delete task")
	 @Test
	 void deleteTaskTest() {
		 
		 willDoNothing().given(taskRepository).deleteById(task.getId());

	        //when
	        taskService.deleteById(task.getId());

	        //then
	        verify(taskRepository,times(1)).deleteById(task.getId());
	 }
	 
	 
	

}
