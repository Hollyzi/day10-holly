package com.example.service;

import com.example.exception.IdNotFoundException;
import com.example.model.TodoItem;
import com.example.repository.TodoListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

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

    @Test
    void should_throw_IdNotFoundException_when_search_id_not_exist() {
        //given
        final TodoListService todoService=buildTodoService();
        Integer notExistId=150;
        when(mockedTodoListRepository.findById(notExistId)).thenReturn(null);
        //when
        //then
        assertThrows(IdNotFoundException.class, () -> todoService.findOne(notExistId));
    }
    private TodoListService buildTodoService(){
        mockedTodoListRepository=mock(TodoListRepository.class);
        return new TodoListService(mockedTodoListRepository);
    }

}