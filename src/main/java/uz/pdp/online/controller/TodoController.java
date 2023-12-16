package uz.pdp.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.model.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final List<Todo> todoList = new ArrayList<>();
    private int idCounter = 1;

    @GetMapping("/show")
    public String showTodos(Model model) {
        model.addAttribute("todos", todoList);
        return "todos";
    }

    @GetMapping("/add")
    public String showAddTodoForm() {
        return "addTodo";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title, @RequestParam String priority) {
        LocalDateTime createdAt = LocalDateTime.now();
        Todo newTodo = new Todo(idCounter++, title, priority, createdAt);
        todoList.add(0, newTodo);
        return "redirect:/todos/show";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteTodoForm(@PathVariable int id, Model model) {
        Todo todoToDelete = findTodoById(id);
        model.addAttribute("todoToDelete", todoToDelete);
        return "deleteTodo";
    }

    @PostMapping("/delete")
    public String deleteTodo(@RequestParam int id) {
        todoList.removeIf(todo -> todo.getId().equals(id));
        return "redirect:/todos/show";
    }

    @GetMapping("/update/{id}")
    public String showUpdateTodoForm(@PathVariable int id, Model model) {
        Todo todoToUpdate = findTodoById(id);
        model.addAttribute("todoToUpdate", todoToUpdate);
        return "updateTodo";
    }

    @PostMapping("/update")
    public String processUpdateTodo(@RequestParam int id, @RequestParam String title, @RequestParam String priority) {
        Todo todoToUpdate = findTodoById(id);
        if (todoToUpdate != null) {
            todoToUpdate.setTitle(title);
            todoToUpdate.setPriority(priority);
        }
        return "redirect:/todos/show";
    }

    private Todo findTodoById(int todoId) {
        return todoList.stream().filter(todo -> todo.getId().equals(todoId)).findFirst().orElse(null);
    }
}
