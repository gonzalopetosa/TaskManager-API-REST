package com.practica.TaskManager.Controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.TaskManager.controller.TaskController;
import com.practica.TaskManager.entities.Task;
import com.practica.TaskManager.entities.User;
import com.practica.TaskManager.services.TaskService;
import com.practica.TaskManager.services.UserService;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskService taskService;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

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
				.country("Argentina").build();
		user.setTasks(new ArrayList<>());
		
		task = Task.builder()
				.id(1).monthCreation("June")
				.data("getTaskByUserId")
				.user(user)
				.build();
	}

	@Test
	void saveTaskTest() throws Exception {
		given(userService.existsById(user.getId())).willReturn(true);
		given(userService.findById(user.getId())).willReturn(Optional.of(user));
		given(userService.save(user)).willReturn(user);
		given(taskService.save(any(Task.class))).willAnswer((Invocation) -> Invocation.getArgument(0));

		ResultActions response = mockMvc.perform(post("/api/TK/task/{userId}", user.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(task)));

		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.monthCreation", is(task.getMonthCreation())))
				.andExpect(jsonPath("$.data", is(task.getData())));
	}

	@Test
	void saveTaskNotExistUserTest() throws Exception {
		given(userService.existsById(user.getId())).willReturn(false);
		given(userService.findById(user.getId())).willReturn(Optional.of(user));
		given(userService.save(user)).willReturn(user);
		given(taskService.save(any(Task.class))).willAnswer((Invocation) -> Invocation.getArgument(0));

		ResultActions response = mockMvc.perform(post("/api/TK/task/{userId}", user.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(task)));

		response.andExpect(status().isNotFound())
		.andDo(print())
		.andExpect(jsonPath("$.data", is("Not found User")));
	}

	@Test
	void getAllTest() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		given(taskService.findAll()).willReturn(tasks);

		ResultActions response = mockMvc.perform(get("/api/TK/tasks"));

		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()", is(tasks.size())));
	}

	@Test
	void getTaskByUserId() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);

		given(userService.existsById(user.getId())).willReturn(true);
		given(userService.findAllTaskByUserId(user.getId())).willReturn(tasks);

		ResultActions response = mockMvc.perform(get("/api/TK/task/{userId}", user.getId()));

		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()", is(tasks.size())));
	}

	@Test
	void getTaskByNotExistUser() throws Exception {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		given(userService.existsById(user.getId())).willReturn(false);
		given(userService.findAllTaskByUserId(user.getId())).willReturn(tasks);

		ResultActions response = mockMvc.perform(get("/api/TK/task/{userId}", user.getId()));

		response.andExpect(status().isNotFound())
		.andDo(print())
		.andExpect(jsonPath("$.data", is("Not found User")));
	}

	@Test
	void updateTaskTest() throws Exception {
		Task modifyTask = Task.builder()
				.id(1).monthCreation("September")
				.data("modify Task data")
				.user(user)
				.build();

		given(taskService.existsById(task.getId())).willReturn(true);
		given(taskService.update(anyInt(), any(Task.class))).willAnswer((invocation) -> invocation.getArgument(1));

		ResultActions response = mockMvc.perform(put("/api/TK/task/{taskId}", task.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifyTask)));

		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.monthCreation", is(modifyTask.getMonthCreation())))
				.andExpect(jsonPath("$.data", is(modifyTask.getData())));
	}

	@Test
	void updateNotExistTaskTest() throws Exception {
		Task modifyTask = Task.builder()
				.id(1)
				.monthCreation("September")
				.data("modify Task data")
				.user(user)
				.build();
		given(taskService.existsById(task.getId())).willReturn(false);
		given(taskService.update(anyInt(), any(Task.class))).willAnswer((invocation) -> invocation.getArgument(1));

		ResultActions response = mockMvc.perform(put("/api/TK/task/{taskId}", task.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifyTask)));

		response.andExpect(status().isNotFound())
		.andDo(print())
		.andExpect(jsonPath("$.data", is("Not found Task")));
	}

	@Test
	void deleteTaskTest() throws Exception {
		given(taskService.existsById(anyInt())).willReturn(true);
		willDoNothing().given(taskService).deleteById(task.getId());

		ResultActions response = mockMvc.perform(delete("/api/TK/task/{taskId}", task.getId()));

		response.andExpect(status().isNoContent())
		.andDo(print());
	}

	@Test
	void deleteNotExistTaskTest() throws Exception {
		given(taskService.existsById(anyInt())).willReturn(false);
		willDoNothing().given(taskService).deleteById(task.getId());

		ResultActions response = mockMvc.perform(delete("/api/TK/task/{taskId}", task.getId()));

		response.andExpect(status().isNotFound())
		.andDo(print())
		.andExpect(jsonPath("$.data", is("Task ID not exist")));
	}
	
}
