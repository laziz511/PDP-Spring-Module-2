package uz.pdp.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.config.security.CustomUserDetails;
import uz.pdp.online.model.Todo;
import uz.pdp.online.repository.AuthRoleRepository;
import uz.pdp.online.repository.TodoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository todoRepository;
    private static final String REDIRECT_TO_HOME_PAGE = "redirect:/todos/show";

    @GetMapping("/show")
    public String showTodos(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userId = userDetails.getAuthUser().getId();
        List<Todo> todos = todoRepository.findByUserId(userId);
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/add")
    public String showAddTodoForm() {
        return "addTodo";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title, @RequestParam String priority, @AuthenticationPrincipal CustomUserDetails userDetails) {
        LocalDateTime createdAt = LocalDateTime.now();
        Long userId = userDetails.getAuthUser().getId();
        Todo newTodo = new Todo(null, title, priority, createdAt, userId);
        todoRepository.save(newTodo);
        return REDIRECT_TO_HOME_PAGE;
    }

    @GetMapping("/delete/{id}")
    public String showDeleteTodoForm(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getAuthUser().getId();
        Todo todoToDelete = todoRepository.findByIdAndUserId(id, userId);
        model.addAttribute("todoToDelete", todoToDelete);
        return "deleteTodo";
    }

    @PostMapping("/delete")
    public String deleteTodo(@RequestParam Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getAuthUser().getId();
        todoRepository.delete(id, userId);
        return REDIRECT_TO_HOME_PAGE;
    }

    @GetMapping("/update/{id}")
    public String showUpdateTodoForm(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getAuthUser().getId();
        Todo todoToUpdate = todoRepository.findByIdAndUserId(id, userId);
        model.addAttribute("todoToUpdate", todoToUpdate);
        return "updateTodo";
    }

    @PostMapping("/update")
    public String updateTodo(@RequestParam Long id, @RequestParam String title, @RequestParam String priority, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getAuthUser().getId();
        Todo todoToUpdate = todoRepository.findByIdAndUserId(id, userId);
        if (todoToUpdate != null) {
            todoToUpdate.setTitle(title);
            todoToUpdate.setPriority(priority);
            todoRepository.update(todoToUpdate);
        }
        return REDIRECT_TO_HOME_PAGE;
    }
}
