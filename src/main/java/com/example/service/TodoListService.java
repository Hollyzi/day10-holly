package com.example.service;

import com.example.model.TodoItem;
import com.example.repository.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;

    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }


    public List<TodoItem> findAll() {
     return todoListRepository.findAll();
    }

    public TodoItem create(TodoItem todoItem) {
    return  todoListRepository.save(todoItem);
    }
}
