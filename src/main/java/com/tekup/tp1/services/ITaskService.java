package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Task;

public interface ITaskService {
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    
    Optional<Task> getTaskById(Long id);
    List<Task> getAllTasks();
}