package uz.pdp.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.model.Todo;
import uz.pdp.online.repository.TodoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoRepository todoRepository;
    private static final String REDIRECT_TO_HOME_PAGE = "redirect:/todos/show";

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/show")
    public String showTodos(Model model) {
        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/add")
    public String showAddTodoForm() {
        return "addTodo";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title, @RequestParam String priority) {
        LocalDateTime createdAt = LocalDateTime.now();
        Todo newTodo = new Todo(null, title, priority, createdAt);
        todoRepository.save(newTodo);
        return REDIRECT_TO_HOME_PAGE;
    }

    @GetMapping("/delete/{id}")
    public String showDeleteTodoForm(@PathVariable int id, Model model) {
        Todo todoToDelete = todoRepository.findById(id);
        model.addAttribute("todoToDelete", todoToDelete);
        return "deleteTodo";
    }

    @PostMapping("/delete")
    public String deleteTodo(@RequestParam int id) {
        todoRepository.delete(id);
        return REDIRECT_TO_HOME_PAGE;
    }

    @GetMapping("/update/{id}")
    public String showUpdateTodoForm(@PathVariable int id, Model model) {
        Todo todoToUpdate = todoRepository.findById(id);
        model.addAttribute("todoToUpdate", todoToUpdate);
        return "updateTodo";
    }

    @PostMapping("/update")
    public String processUpdateTodo(@RequestParam int id, @RequestParam String title, @RequestParam String priority) {
        Todo todoToUpdate = todoRepository.findById(id);
        if (todoToUpdate != null) {
            todoToUpdate.setTitle(title);
            todoToUpdate.setPriority(priority);
            todoRepository.update(todoToUpdate);
        }
        return REDIRECT_TO_HOME_PAGE;
    }
}
