package com.superstar_rewards.app.task;

import com.superstar_rewards.app.account.Account;
import com.superstar_rewards.app.auth.AuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(HttpServletRequest request, @Valid @RequestBody CreateTaskRequest body) {
        Account account = (Account) request.getAttribute(AuthFilter.AUTHENTICATED_ACCOUNT_ATTRIBUTE);
        return taskService.createTask(account.getId(), body);
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(HttpServletRequest request, @PathVariable("id") Long id) {
        Account account = (Account) request.getAttribute(AuthFilter.AUTHENTICATED_ACCOUNT_ATTRIBUTE);
        return taskService.getTask(account.getId(), id);
    }

    @GetMapping("/me")
    public List<TaskResponse> listMyTasks(HttpServletRequest request) {
        Account account = (Account) request.getAttribute(AuthFilter.AUTHENTICATED_ACCOUNT_ATTRIBUTE);
        return taskService.listTasks(account.getId());
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(
            HttpServletRequest request,
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateTaskRequest body
    ) {
        Account account = (Account) request.getAttribute(AuthFilter.AUTHENTICATED_ACCOUNT_ATTRIBUTE);
        return taskService.updateTask(account.getId(), id, body);
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateTaskStatus(
            HttpServletRequest request,
            @PathVariable("id") Long id,
            @RequestParam("status") TaskStatus status
    ) {
        Account account = (Account) request.getAttribute(AuthFilter.AUTHENTICATED_ACCOUNT_ATTRIBUTE);
        return taskService.updateStatus(account.getId(), id, status);
    }
}
