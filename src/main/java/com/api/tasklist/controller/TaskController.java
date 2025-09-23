package com.api.tasklist.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.tasklist.model.Task;
import com.api.tasklist.repository.TaskRepository;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    //Done
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

   // Problems
    @GetMapping("/id")
    public ResponseEntity<Task> getTaskById(@PathVariable Long Id){
        return taskRepository.findById(Id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Done
    @GetMapping("/status/completed")
    public List<Task> getCompletedTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> "completed".equalsIgnoreCase(task.getStatus()))
                .toList();
    }

    //Done
    @GetMapping("/status/uncompleted")
    public List<Task> getUncompletedTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> "uncompleted".equalsIgnoreCase(task.getStatus()))
                .toList();
    }

    //Done
    @PostMapping("/addtask")
    public String addTask(@RequestParam String name, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate due_date) {

        Task newTask = new Task();

        newTask.setName(name);
        newTask.setStatus("uncompleted");
        newTask.setDue_Date(due_date);

        taskRepository.save(newTask);

        return "Task was successfully added";
    }


    //Done
    @PostMapping("/mark")
    public String markTaskAsCompleted(@RequestParam Long id) {
        if(taskRepository.findById(id).isEmpty()){
            throw new RuntimeException("Task not found with id " + id);
        }else
        {
            Task task = taskRepository.findById(id).get();

            task.setStatus("completed");

            taskRepository.save(task);

            return "Task id " + id + " has been marked as Completed.";
        }
    }

    //Done
    @PostMapping("/unmark")
    public String markTaskAsUncompleted(@RequestParam Long id) {
         if(taskRepository.findById(id).isEmpty()){
            throw new RuntimeException("Task not found with id " + id);
        }else
        {
            Task task = taskRepository.findById(id).get();

            task.setStatus("uncompleted");

            taskRepository.save(task);

            return "Task id " + id + " has been marked as Uncompleted.";
        }
    }

   
    
}
