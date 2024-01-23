package com.practica.TaskManager.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
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
import com.practica.TaskManager.repository.UserRepository;
import com.practica.TaskManager.services.UserService;



@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;

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
	}
	
	@DisplayName("save user test")
    @Test
    void saveUserTest() {
		given(userRepository.save(user)).willReturn(user);
		
		User response = userService.save(user);
		
		assertThat(response).isNotNull();
	}
	
	@DisplayName("Get all users")
	@Test
	void getAllUsersTest(){
		User user2 = User.builder()
				.fullName("Gonzalo N")
				.email("g1@gmail.com")
				.phoneNumber("3333-2222")
				.gender("male")
				.country("Argentina")
				.build();
		
		given(userRepository.findAll()).willReturn(List.of(user,user2));
		
		List<User> response = userService.findAll();
		
		assertThat(response).isNotNull();
		assertThat(response.size()).isEqualTo(2);
	}
	
	@DisplayName("Test to return an empty list")
    @Test
    void testListarColeccionEmpleadosVacia(){
        
        given(userRepository.findAll()).willReturn(Collections.emptyList());
    
        List<User> response = userService.findAll();

        assertThat(response).isEmpty();
        assertThat(response.size()).isEqualTo(0);
    }
	
	@DisplayName("get user by id")
    @Test
    void getUserByIdTest() {
		given(userRepository.findById(1)).willReturn(Optional.of(user));
		
		Optional<User> response = userService.findById(user.getId());
		
		assertThat(response.get()).isNotNull();
	}
	
	@DisplayName("modify User")
	@Test
	void modifyUser() throws Exception{
		User modifyUser = User.builder()
							.id(1)
							.fullName("Raul Gonzales")
							.email("gonzales@gmail.com")
							.phoneNumber("2222-2222")
							.gender("male")
							.country("Argentina")
							.build();
		
		given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
		
		User response = userService.update(user.getId(), modifyUser);
		
		assertThat(response.getFullName()).isEqualTo("Raul Gonzales");
		assertThat(response.getEmail()).isEqualTo("gonzales@gmail.com");

	}
	
	 @DisplayName("update task with non-existing id")
	 @Test
	 void updateUserWithNonExistingIdTest() {
		 User modifyUser = User.builder()
					.id(1)
					.fullName("Raul Gonzales")
					.email("gonzales@gmail.com")
					.phoneNumber("2222-2222")
					.gender("male")
					.country("Argentina")
					.build();

	        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

	        assertThrows(Exception.class, () -> userService.update(user.getId(), modifyUser));

	        verify(userRepository, never()).save(any());
	    }
	
	@DisplayName("delete User")
	@Test
	void deleteUserTest() {
		willDoNothing().given(userRepository).deleteById(user.getId());
		
		userService.deleteById(user.getId());
		
		verify(userRepository,times(1)).deleteById(user.getId());
	}
}
