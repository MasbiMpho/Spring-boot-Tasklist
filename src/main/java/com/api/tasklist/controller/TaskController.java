package com.api.tasklist.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.tasklist.model.Task;
import com.api.tasklist.repository.TaskRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("")
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;


    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    //Done w/ logging
    @Operation(summary = "Get All Tasks", description = "Retrieve a list of all tasks")
    @GetMapping("/")
    public List<Task> getAllTasks() {        
        logger.info("Fetching all tasks");

        return taskRepository.findAll();
    }

   // Done w/ logging
   @Operation(summary = "Get Task by ID", description = "Retrieve a task by its ID")
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        logger.info("Fetching task with ID: {}", id);

        return taskRepository.findById(id)
                .map(task -> {
                    logger.debug("Task found: {}", task);
                    return ResponseEntity.ok(task);
                })
                .orElseGet(() -> {
                    logger.warn("Task not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    //Done w logging
    @Operation(summary = "Get Completed Tasks", description = "Retrieve a list of all completed tasks")
    @GetMapping("/status/completed")
    public ResponseEntity<List<Task>> getCompletedTasks() {
        logger.info("Fetching completed tasks");

        List<Task> completedTasks = taskRepository.findAll().stream()
                .filter(task -> "completed".equalsIgnoreCase(task.getStatus()))
                .toList();

        if (completedTasks.isEmpty()) {
            logger.warn("No completed tasks were found");
            return ResponseEntity.noContent().build();
        } else {
            logger.debug("Found {} completed task(s)", completedTasks.size());
            return ResponseEntity.ok(completedTasks);
        }
    }
    
    //Done w/ logging
    @Operation(summary = "Get Uncompleted Tasks", description = "Retrieve a list of all uncompleted tasks")
    @GetMapping("/status/uncompleted")
    public ResponseEntity<List<Task>> getUncompletedTasks() {
        logger.info("Fetching uncompleted tasks");

        List<Task> uncompletedTasks = taskRepository.findAll().stream()
                .filter(task -> "uncompleted".equalsIgnoreCase(task.getStatus()))
                .toList();

        if (uncompletedTasks.isEmpty()) {
            logger.warn("No uncompleted tasks were found");
            return ResponseEntity.noContent().build();
        } else {
            logger.debug("Found {} uncompleted task(s)", uncompletedTasks.size());
            return ResponseEntity.ok(uncompletedTasks);
        }
    }

    //Done w/ logging
    @Operation(summary = "Add New Task", description = "Add a new task with a name and due date")
    @PostMapping("/addtask")
    public ResponseEntity<String> addTask(@RequestParam String name, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate due_date) {
        logger.info("Adding new task: {} with due date: {}", name, due_date);
        
        Task newTask = new Task();
        newTask.setName(name);
        newTask.setStatus("uncompleted");
        newTask.setDue_Date(due_date);

        try {
            taskRepository.save(newTask);
            logger.debug("Task successfully added: {}", newTask);

            return ResponseEntity.ok("Task was successfully added");
        } catch (Exception e) {
            logger.error("Error occurred while adding task: {}", e.getMessage());

            return ResponseEntity.status(500).body("An error occurred while adding the task");
        }
    }

    //Done w/ logging
    @Operation(summary = "Mark Task as Completed", description = "Mark a task as completed by its ID")
    @PutMapping("/mark")
    public ResponseEntity<String> markTaskAsCompleted(@RequestParam Long id) {
        logger.info("Marking task with ID " + id + " as Completed.");

        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus("completed");
                    taskRepository.save(task);

                    String message = "Task id " + id + " has been marked as Completed.";
                    logger.debug(message);
                    return ResponseEntity.ok(message);
                })
                .orElseGet(() -> {
                    String errorMessage = "Task not found with id " + id;
                    logger.warn(errorMessage);
                    return ResponseEntity.status(404).body(errorMessage);
                });
    }

    
    //Done w/ logging
    @Operation(summary = "Mark Task as Uncompleted", description = "Mark a task as uncompleted by its ID")
    @PutMapping("/unmark")
    public ResponseEntity<String> markTaskAsUncompleted(@RequestParam Long id) {
        logger.info("Marking task with ID " + id + " as Uncompleted.");
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus("uncompleted");
                    taskRepository.save(task);

                    String message = "Task id " + id + " has been marked as Uncompleted.";
                    logger.debug(message);
                    return ResponseEntity.ok(message);
                })
                .orElseGet(() -> {
                    String errorMessage = "Task not found with id " + id;
                    logger.warn(errorMessage);
                    return ResponseEntity.status(404).body(errorMessage);
                });
    }

    @Operation(summary = "Update Task", description = "Update a task's name and due date by its ID")
    @PutMapping("/updatetask")
    public ResponseEntity<String> updateTask(@RequestParam Long id, @RequestParam String name, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate due_date) {
        logger.info("Updating task with ID: {}", id);

        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(name);
                    task.setDue_Date(due_date);
                    task.setStatus("uncompleted");
                    taskRepository.save(task);

                    String message = "Task id " + id + " has been updated.";
                    logger.debug(message);
                    return ResponseEntity.ok(message);
                })
                .orElseGet(() -> {
                    String errorMessage = "Task not found with id " + id;
                    logger.warn(errorMessage);
                    return ResponseEntity.status(404).body(errorMessage);
                });
    }

    @Operation(summary = "Delete Task", description = "Delete a task by its ID")
    @DeleteMapping("/deletetask")
    public ResponseEntity<String> deleteTask(@RequestParam Long id) {
        logger.info("Deleting task with ID: {}", id);

        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);

                    String message = "Task id " + id + " has been deleted.";
                    logger.debug(message);
                    return ResponseEntity.ok(message);
                })
                .orElseGet(() -> {
                    String errorMessage = "Task not found with id " + id;
                    logger.warn(errorMessage);
                    return ResponseEntity.status(404).body(errorMessage);
                });
    }
 
    
}
