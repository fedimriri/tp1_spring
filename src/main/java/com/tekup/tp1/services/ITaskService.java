package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Task;
import com.tekup.tp1.exception.TaskNotFoundException;

public interface ITaskService {
    Task createTask(Task task);
    Task updateTask(Long id, Task task) throws TaskNotFoundException;
    void deleteTask(Long id) throws TaskNotFoundException;
    
    Optional<Task> getTaskById(Long id);
    List<Task> getAllTasks();
}
