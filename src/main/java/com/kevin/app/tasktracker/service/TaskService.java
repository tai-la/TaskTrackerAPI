package com.kevin.app.tasktracker.service;

import DTO.TaskCreateRequest;
import com.kevin.app.tasktracker.entity.Task;
import com.kevin.app.tasktracker.repository.TaskRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task getTaskById (Integer id){
        Task exitsingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find task with id: " + id));
        return exitsingTask;
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public Task createTask(TaskCreateRequest req){
        Task task = new Task();
        task.setName(req.getName());
        task.setParentId(req.getParentId());
        task.setDone(false);
        return taskRepository.save(task);
    }

    public Task updateNameTask(Integer id, String newName){
        if (newName == null) {
            throw new RuntimeException("New name cannot be null");
        }
        Task exitsingTask = getTaskById(id);
        exitsingTask.setName(newName);
        return taskRepository.save(exitsingTask);
    }

    public Task updateIsDoneTask(Integer id){
        Task exitsingTask = getTaskById(id);

        if (exitsingTask.getDone() == true) exitsingTask.setDone(false); else exitsingTask.setDone(true);
        return taskRepository.save(exitsingTask);
    }

    public Task updateParentIdTask(Integer id, Integer parentId){
        if (id == null) {
            throw new RuntimeException("Id cannot be null");
        }

        if (id.equals(parentId))
        {
            throw new RuntimeException("Task cannot be their parents");
        }

        Task existingTask = getTaskById(id);
        if (existingTask == null) {
            throw new RuntimeException("Cannot find task with id: " + id);
        }

        if (parentId != null){
            Task parent = getTaskById(parentId);
            if (parent != null){
                existingTask.setParentId(parentId);
            }
        }
        else existingTask.setParentId(null);

        return taskRepository.save(existingTask);
    }

    public Task deleteTask(Integer id){
        Task exitsingTask = getTaskById(id);
        if (exitsingTask == null){
            return null;
        }
        taskRepository.delete(exitsingTask);
        return exitsingTask;
    }
}
