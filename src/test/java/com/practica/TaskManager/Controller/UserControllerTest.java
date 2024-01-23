package com.practica.TaskManager.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.TaskManager.controller.UserController;
import com.practica.TaskManager.entities.User;
import com.practica.TaskManager.services.UserService;


@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private User user;

	@BeforeEach
	void setup() {
		user = User.builder()
				.fullName("Gonzalo N")
				.email("g1@gmail.com")
				.phoneNumber("3333-2222")
				.gender("male")
				.country("Argentina")
				.build();
	}


	@Test
	void saveUserTest() throws Exception{
		given(userService.save(any(User.class))).willAnswer((Invocation) -> Invocation.getArgument(0));

		ResultActions response = mockMvc.perform(post("/api/TK/user/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)));

		response.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.fullName",is(user.getFullName())))
		.andExpect(jsonPath("$.email",is(user.getEmail())))
		.andExpect(jsonPath("$.phoneNumber",is(user.getPhoneNumber())))
		.andExpect(jsonPath("$.gender",is(user.getGender())))
		.andExpect(jsonPath("$.country",is(user.getCountry())));
	}

	@Test
	void saveUserWithIdTest() throws Exception{
		user.setId(1);
		given(userService.save(any(User.class))).willAnswer((Invocation) -> Invocation.getArgument(0));

		ResultActions response = mockMvc.perform(post("/api/TK/user/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)));

		response.andDo(print())
		.andExpect(status().isBadRequest());
	}

	@Test
	void getAllTest() throws Exception{
		List<User> users = new ArrayList<User>();
		User user2 = User.builder()
				.id(2)
				.fullName("Gonzalo P")
				.email("g2@gmail.com")
				.phoneNumber("2222-2222")
				.gender("male")
				.country("Argentina")
				.build();
		users.add(user);
		users.add(user2);

		given(userService.findAll()).willReturn(users);

		ResultActions response = mockMvc.perform(get("/api/TK/users"));

		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()",is(users.size())));
	}

	@Test
	void getUserByIdTest() throws Exception{
		user.setId(1);
		given(userService.existsById(user.getId())).willReturn(true);
		given(userService.findById(user.getId())).willReturn(Optional.of(user));

		ResultActions response = mockMvc.perform(get("/api/TK/user/{id}",user.getId()));

		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.fullName",is(user.getFullName())))
		.andExpect(jsonPath("$.email",is(user.getEmail())))
		.andExpect(jsonPath("$.phoneNumber",is(user.getPhoneNumber())))
		.andExpect(jsonPath("$.gender", is(user.getGender())))
		.andExpect(jsonPath("$.country", is(user.getCountry())));
	}

	@Test
	void getUserByIdNotFoundIdTest() throws Exception {
		user.setId(1);
		given(userService.existsById(user.getId())).willReturn(false);
		given(userService.findById(user.getId())).willReturn(Optional.of(user));

		ResultActions response = mockMvc.perform(get("/api/TK/user/{id}", user.getId()));

		response.andExpect(status().isNotFound())
		.andDo(print())
		.andExpect(jsonPath("$.data", is("not found id")));
	}

	@Test
	void updateUserTest() throws Exception {
		user.setId(1);
		User modifyUser = User.builder()
				.id(3)
				.fullName("Gonzalo N")
				.email("g1@gmail.com")
				.phoneNumber("3160-1380")
				.gender("male")
				.country("Peru")
				.build();

		given(userService.existsById(anyInt())).willReturn(true);
		given(userService.findById(anyInt())).willReturn(Optional.of(user));
		given(userService.update(anyInt(), any(User.class)))
		.willAnswer((invocation) -> invocation.getArgument(1));

		ResultActions response = mockMvc.perform(put("/api/TK/user/update/{id}", user.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifyUser)));

		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.fullName", is(modifyUser.getFullName())))
		.andExpect(jsonPath("$.email", is(modifyUser.getEmail())))
		.andExpect(jsonPath("$.phoneNumber", is(modifyUser.getPhoneNumber())))
		.andExpect(jsonPath("$.gender", is(modifyUser.getGender())))
		.andExpect(jsonPath("$.country", is(modifyUser.getCountry())));
	}

	@Test
	void updateNotExistUserTest() throws Exception {
		user.setId(1);
		User modifyUser = User.builder()
				.id(3)
				.fullName("Gonzalo N")
				.email("g1@gmail.com")
				.phoneNumber("3160-1380")
				.gender("male")
				.country("Peru")
				.build();

		given(userService.existsById(user.getId())).willReturn(false);
		given(userService.findById(user.getId())).willReturn(Optional.of(user));
		given(userService.update(anyInt(), any(User.class)))
		.willAnswer((invocation) -> invocation.getArgument(1));

		ResultActions response = mockMvc.perform(put("/api/TK/user/update/{id}", user.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifyUser)));

		response.andExpect(status().isNotFound())
		.andDo(print())
		.andExpect(jsonPath("$.data", is("Trying to update a non existent user")));
	}

	@Test
	void deleteUserTest() throws Exception {
		user.setId(1);
		Integer UserId = 1;
		given(userService.existsById(anyInt())).willReturn(true);
		willDoNothing().given(userService).deleteById(1);

		ResultActions response = mockMvc.perform(delete("/api/TK/user/delete/{id}",UserId));

		response.andExpect(status().isNoContent())
		.andDo(print());
	}

	@Test
	void deleteNotExistUserTest() throws Exception {
		Integer UserId = 1;
		given(userService.existsById(anyInt())).willReturn(false);
		willDoNothing().given(userService).deleteById(1);

		ResultActions response = mockMvc.perform(delete("/api/TK/user/delete/{id}",UserId));

		response.andExpect(status().isNotFound())
		.andDo(print())
		.andExpect(jsonPath("$.data", is("not found id")));
	}
	
}
