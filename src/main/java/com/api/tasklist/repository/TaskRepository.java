package com.api.tasklist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.api.tasklist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @NonNull
    @Override
    Optional<Task> findById(Long id);
    
}
