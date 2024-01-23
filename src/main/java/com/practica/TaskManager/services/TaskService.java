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
import com.practica.TaskManager.repository.TaskRepository;

@Service
public class TaskService implements TaskRepository{

	
	@Autowired
	TaskRepository repository;
	
	@Override
	public List<Task> findAll() {
		return repository.findAll();
	}
	
	@Override
	public <S extends Task> S save(S entity) {
		return repository.save(entity);
	}

	@Override
	public Optional<Task> findById(Integer id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return repository.existsById(id);
	}
	
	@Override
	public void deleteById(Integer id){
		repository.deleteById(id);
	}
	
	public Task update(Integer taskId, Task task) throws Exception {
		try {
			Optional<Task> optionaTask = repository.findById(taskId);		
			if(optionaTask.isPresent()) {	
				Task presentTask = optionaTask.get();
				presentTask.setData(task.getData());
				presentTask.setMonthCreation(task.getMonthCreation());
				repository.save(presentTask);
				return task;
			}else {
				 throw new Exception("Usuario no encontrado");
			}
		} catch (Exception e) {
			 throw new Exception(e.getCause());
		}
	}
	
	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Task> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Task> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Task> entities) {
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
	public Task getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Task> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Task> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Task> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void delete(Task entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Task> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Task> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Task> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Task> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends Task> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Task> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Task> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Task, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
