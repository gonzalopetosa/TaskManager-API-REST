package com.practica.TaskManager.services;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.practica.TaskManager.entities.Task;
import com.practica.TaskManager.entities.User;
import com.practica.TaskManager.repository.UserRepository;

@Service
public class UserService implements UserRepository{

	@Autowired
	private UserRepository repository;
	
	@Override
	public List<User> findAll(){
		return repository.findAll();
	}
	@Override
	public <S extends User> S save(S entity) {
		return repository.save(entity);
	}

	@Override
	public Optional<User> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return repository.existsById(id);
	}
	
	@Override
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public void delete(User entity) {
		repository.delete(entity);
	}
	
	public User update(Integer id, User user) throws Exception {
		try {
			User newUser = repository.findById(id).get();

			newUser.setFullName(user.getFullName());
			newUser.setEmail(user.getEmail());
			newUser.setPhoneNumber(user.getPhoneNumber());
			newUser.setGender(user.getGender());
			newUser.setCountry(user.getCountry());

			repository.save(newUser);
			return user;

		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public List<Task> findAllTaskByUserId(Integer id) throws Exception {
		try {
			Optional<User> entityOptional = repository.findById(id);
			 if (entityOptional.isPresent()) {
		            User existingUser = entityOptional.get();
		            return existingUser.getTasks();
			 }else {
				 throw new Exception("Usuario no encontrado");
			 }
		}catch (Exception e) {
			throw new Exception(e.getMessage(), e.getCause());
		}
	}
	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends User> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<User> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(Integer id) {
//		return repository.getById(id);
		return null;
	}

	@Override
	public User getReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<User> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends User> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends User> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends User, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}

}
