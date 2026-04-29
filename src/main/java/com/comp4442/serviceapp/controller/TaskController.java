package com.comp4442.serviceapp.controller;

import com.comp4442.serviceapp.dto.TaskRequest;
import com.comp4442.serviceapp.entity.TaskEntity;
import com.comp4442.serviceapp.repository.TaskRepository;
import com.comp4442.serviceapp.service.ComputeService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final ComputeService computeService;

    public TaskController(TaskRepository taskRepository, ComputeService computeService) {
        this.taskRepository = taskRepository;
        this.computeService = computeService;
    }

    @PostMapping
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskRequest request) {
        TaskEntity task = new TaskEntity();
        task.setExpression(request.getExpression());
        task.setStatus("PENDING");
        task = taskRepository.save(task);
        computeAsync(task);
        return ResponseEntity.ok(task);
    }

    @Async
    protected void computeAsync(TaskEntity task) {
        try {
            task.setStatus("PROCESSING");
            taskRepository.save(task);
            double result = computeService.evaluate(task.getExpression());
            task.setStatus("COMPLETED");
            task.setResult(result);
        } catch (IllegalArgumentException e) {
            task.setStatus("FAILED");
            task.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            task.setStatus("FAILED");
            task.setErrorMessage("Internal error: " + e.getMessage());
        } finally {
            task.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(task);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTask(@PathVariable String id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(taskRepository.findAll());
    }
}