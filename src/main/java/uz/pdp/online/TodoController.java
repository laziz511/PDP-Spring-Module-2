package uz.pdp.online;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoController {

    private final List<Todo> todoList;

    public TodoController() {
        // Initialize the todoList
        todoList = new ArrayList<>();
        todoList.add(new Todo(1L, "Learn Spring", false));
        todoList.add(new Todo(2L, "Build REST API", true));
    }

    @GetMapping("/todos")
    @ResponseBody
    public String getAllTodos() {
        StringBuilder htmlTable = buildHtmlTable();
        return htmlTable.toString();
    }

    private StringBuilder buildHtmlTable() {
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("<html><body><table border=\"1\">");
        htmlTable.append("<tr><th>ID</th><th>Title</th><th>Completed</th></tr>");

        for (Todo todo : todoList) {
            htmlTable.append("<tr>");
            htmlTable.append("<td>").append(todo.getId()).append("</td>");
            htmlTable.append("<td>").append(todo.getTitle()).append("</td>");
            htmlTable.append("<td>").append(todo.isCompleted()).append("</td>");
            htmlTable.append("</tr>");
        }

        htmlTable.append("</table></body></html>");
        return htmlTable;
    }
}
