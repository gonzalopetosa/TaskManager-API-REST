package com.practica.TaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practica.TaskManager.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
