package com.example.controller;

import com.example.model.TodoItem;
import com.example.repository.TodoListRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class TodoListControllerTest {
    @Autowired
    private MockMvc client;
    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private JacksonTester<List<TodoItem>> todoItemJacksonTester;
    private TodoItem todoItem;
    private TodoItem testdata2;

    @BeforeEach
    void setup() {
        todoListRepository.deleteAll();
        todoListRepository.flush();

        todoItem = new TodoItem("testMock", false);
        todoListRepository.save(todoItem);
        testdata2 = new TodoItem("testdata2", false);
        todoListRepository.save(testdata2);
    }

    @Test
    void should_return_all_todoItems_when_call_getAll() throws Exception {
        //Given
        final List<TodoItem> givenTodos = todoListRepository.findAll();
        //When
        final MvcResult result = client.perform(MockMvcRequestBuilders.get("/todos")).andReturn();
        //Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        final List<TodoItem> fetchedTodoItems = todoItemJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedTodoItems).hasSameSizeAs(givenTodos);
        assertThat(fetchedTodoItems)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenTodos);
    }

    @Test
    void should_update_todoItem() throws Exception {
        //Given
        List<TodoItem> todoItems = todoListRepository.findAll();
        Integer givenId = todoItems.get(0).getId();
        String givenText = "update text";
        Boolean givenDone = false;
        String givenTodoItem = String.format("{\"id\": %s, \"text\": \"%s\", \"done\": \"%s\"}",
                givenId,
                givenText,
                givenDone
        );
        //When
        //Then
        client.perform(MockMvcRequestBuilders.put("/todos/" + givenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenTodoItem))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNumber()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.text").value(givenText)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.done").value(givenDone)
                );
        List<TodoItem> afterTodoItems = todoListRepository.findAll();
        AssertionsForInterfaceTypes.assertThat(afterTodoItems).hasSize(2);
        AssertionsForClassTypes.assertThat(afterTodoItems.get(0).getId()).isEqualTo(givenId);
        AssertionsForClassTypes.assertThat(afterTodoItems.get(0).getText()).isEqualTo(givenText);
        AssertionsForClassTypes.assertThat(afterTodoItems.get(0).getDone()).isEqualTo(givenDone);
    }

    @Test
    void should_return_no_content_when_call_delete() throws Exception {
        //Given
        var toDeleteTodoId = testdata2.getId();

        //When
        final MvcResult result = client.perform(MockMvcRequestBuilders.delete("/todos/2")).andReturn();
        //Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(result.getResponse()).isEqualTo(toDeleteTodoId);
        assertThat(todoListRepository.findAll()).hasSize(1);

    }

    @Test
    void should_create_TodoItem_success() throws Exception {
    //Given
        todoListRepository.deleteAll();
        String givenText="add item";
        Boolean givenDone=false;
        String givenTodoItem=String.format("{\"text\":\"%s\",\"done\":\"%s\"}",givenText,givenDone);

    //When
    //Then
          client.perform(MockMvcRequestBuilders.post("/todos")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(givenTodoItem))
                  .andExpect(MockMvcResultMatchers.status().isCreated())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(givenText))
                  .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(givenDone));
        List<TodoItem> todoItems = todoListRepository.findAll();
        assertThat(todoItems).hasSize(1);
        assertThat(todoItems.get(0).getId()).isEqualTo(todoItems.get(0).getId());
        assertThat(todoItems.get(0).getText()).isEqualTo(givenText);
        assertThat(todoItems.get(0).getDone()).isEqualTo(givenDone);
    }

}