package com.kevin.app.tasktracker.controller;

import DTO.TaskCreateRequest;
import com.kevin.app.tasktracker.entity.Task;
import com.kevin.app.tasktracker.service.TaskService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping()
    public List<Task> getAllTasks(){
        return taskService.getAllTask();
    }

    @PostMapping
    public Task createTask(@RequestBody TaskCreateRequest task){
        return taskService.createTask(task);
    }

    @PatchMapping("/{id}/name")
    public Task editTaskName(@PathVariable Integer id, @RequestParam String newName){
        return taskService.updateNameTask(id, newName);
    }

    @PatchMapping("/{id}/status")
    public Task toggleTaskStatus(@PathVariable Integer id){
        return taskService.updateIsDoneTask(id);
    }

    @PatchMapping("/{id}/parent-id")
    public Task updateParentId(@PathVariable Integer id, @RequestParam Integer parentId){
        return taskService.updateParentIdTask(id, parentId);
    }

    @DeleteMapping("/{id}")
    public Task deleteTask(@PathVariable Integer id){
        return taskService.deleteTask(id);
    }

}
