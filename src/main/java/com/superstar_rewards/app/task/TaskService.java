package com.superstar_rewards.app.task;

import com.superstar_rewards.app.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(Long accountId, CreateTaskRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Task task = Task.create(
                accountId,
                request.getTitle(),
                request.getDescription(),
                request.getRewardAmount(),
                now
        );
        Task saved = taskRepository.save(task);
        return TaskResponse.fromTask(saved);
    }

    public TaskResponse getTask(Long accountId, Long taskId) {
        Task task = taskRepository.findByIdAndAccountId(taskId, accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return TaskResponse.fromTask(task);
    }

    public List<TaskResponse> listTasks(Long accountId) {
        return taskRepository.findAllByAccountId(accountId)
                .stream()
                .map(TaskResponse::fromTask)
                .toList();
    }

    public TaskResponse updateTask(Long accountId, Long taskId, UpdateTaskRequest request) {
        Task task = taskRepository.findByIdAndAccountId(taskId, accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        TaskStatus currentStatus = TaskStatus.fromValue(task.getStatus());
        if (currentStatus == TaskStatus.COMPLETED || currentStatus == TaskStatus.CANCELLED) {
            throw new IllegalStateException("Cannot update a task that is " + currentStatus.name().toLowerCase());
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setRewardAmount(request.getRewardAmount());
        task.setStatus(request.getStatus().getValue());
        if (request.getStatus() == TaskStatus.COMPLETED) {
            task.setCompletedAt(LocalDateTime.now());
        }
        task.setUpdatedAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);
        return TaskResponse.fromTask(saved);
    }

    public TaskResponse updateStatus(Long accountId, Long taskId, TaskStatus status) {
        Task task = taskRepository.findByIdAndAccountId(taskId, accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setStatus(status.getValue());
        if (status == TaskStatus.COMPLETED) {
            task.setCompletedAt(LocalDateTime.now());
        }
        task.setUpdatedAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);
        return TaskResponse.fromTask(saved);
    }
}
