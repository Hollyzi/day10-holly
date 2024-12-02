package com.example.service;

import com.example.model.TodoItem;
import com.example.repository.TodoListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoListServiceTest {
    @Mock
    TodoListRepository mockedTodoListRepository;
    @Test
    void should_return_the_given_todoItem_when_getAlltodoItems(){
    //Given
        final TodoListService todoListService=buildTodoService();
        when(mockedTodoListRepository.findAll()).thenReturn(List.of(new TodoItem("mocktodo",false)));

    //When
        List<TodoItem> todoItems=todoListService.findAll();
    //Then
        assertEquals(1,todoItems.size());
        assertEquals("mocktodo",todoItems.get(0).getText());
    }
    private TodoListService buildTodoService(){
        mockedTodoListRepository=mock(TodoListRepository.class);
        return new TodoListService(mockedTodoListRepository);
    }

}