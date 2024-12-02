package com.example.controller;

import com.example.model.TodoItem;
import com.example.service.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public List<TodoItem> getTodoItems() {
        return todoListService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public TodoItem addTodoItem(@RequestBody TodoItem todoItem) {
        return todoListService.create(todoItem);
    }

    @PutMapping("/{id}")
    public TodoItem updateTodoItem(@PathVariable Integer id,@RequestBody TodoItem todoItem){
        return todoListService.update(id,todoItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoItem(@PathVariable Integer id){
        todoListService.delete(id);
    }
}
