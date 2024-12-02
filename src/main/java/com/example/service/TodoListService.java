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
        return todoListRepository.save(todoItem);
    }

    public TodoItem update(Integer id, TodoItem todoItem) {
        TodoItem todoItemById = todoListRepository.findById(id).orElse(null);
        Boolean doneUpdate = todoItem.getDone();
        String textUpdate = todoItem.getText();
        todoItemById.setDone(doneUpdate);
        todoItemById.setText(textUpdate);
        return todoListRepository.save(todoItemById);
    }

    public void delete(Integer id) {
        todoListRepository.deleteById(id);
    }

    public TodoItem findOne(Integer id) {
        return todoListRepository.findById(id).orElse(null);
    }
}
