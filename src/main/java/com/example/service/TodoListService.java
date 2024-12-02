package com.example.service;

import com.example.exception.IdNotFoundException;
import com.example.model.TodoItem;
import com.example.repository.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return todoListRepository.save(todoItem);
    }

    public TodoItem update(Integer id, TodoItem todoItem) {
        TodoItem todoItemById = todoListRepository.findById(id).orElse(null);
        if (todoItem.getDone() != null) {
            todoItemById.setDone(todoItem.getDone());
        }
        if (todoItem.getText() != null) {
            todoItemById.setText(todoItem.getText());
        }
        return todoListRepository.save(todoItemById);
    }

    public void delete(Integer id) {
        todoListRepository.deleteById(id);
    }

    public TodoItem findOne(Integer id) {
        Optional<TodoItem> todoItem = todoListRepository.findById(id);
        if (todoItem == null) throw new IdNotFoundException();
        return todoItem.get();
    }
}
