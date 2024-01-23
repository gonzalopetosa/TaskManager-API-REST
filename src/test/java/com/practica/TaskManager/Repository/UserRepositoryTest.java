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

import com.practica.TaskManager.entities.User;
import com.practica.TaskManager.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	
	@Autowired
	private UserRepository userRepository;
	
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
	
	@DisplayName("Save user")
	@Test
	void saveUserTest() {
	
		User savedUser = userRepository.save(user);
		
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getId()).isGreaterThan(0);
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
	
		userRepository.save(user);
		userRepository.save(user2);
		
		List<User> response = userRepository.findAll();
		
		assertThat(response).isNotNull();
		assertThat(response.size()).isGreaterThan(2);
	}
	
	@DisplayName("get user by id")
    @Test
    void getUserByIdTest() {
		User user2 = User.builder()
				.fullName("Gonzalo N")
				.email("g1@gmail.com")
				.phoneNumber("3333-2222")
				.gender("male")
				.country("Argentina")
				.build();
		userRepository.save(user2);
		
		Optional<User> response = userRepository.findById(user2.getId());
		
		assertThat(response.get()).isNotNull();
	}
	
	@DisplayName("get user by not exist id")
    @Test
    void getUserByNotExistIdTest() {
		User user2 = User.builder()
				.fullName("Gonzalo N")
				.email("g1@gmail.com")
				.phoneNumber("3333-2222")
				.gender("male")
				.country("Argentina")
				.build();
		userRepository.save(user2);
		
		Optional<User> response = userRepository.findById(20);
		
		assertFalse(response.isPresent());
	}
		
	@DisplayName("modify user")
	@Test
	void modifyUserTest() {
		userRepository.save(user);
		User userSaved = userRepository.findById(user.getId()).get();
		userSaved.setEmail("gonzaloN@gmail.com");
		userSaved.setFullName("Gonzalo Nahuel");
		User modifyUser = userRepository.save(userSaved);
		
		assertThat(modifyUser.getEmail()).isEqualTo("gonzaloN@gmail.com");
		assertThat(modifyUser.getFullName()).isEqualTo("Gonzalo Nahuel");
	}
	
	@DisplayName("delete User")
	@Test
	void deleteUserTest() {
		userRepository.save(user);
		
		userRepository.deleteById(user.getId());
		
		Optional<User> response = userRepository.findById(user.getId());
		
		assertFalse(response.isPresent());

	}

}
