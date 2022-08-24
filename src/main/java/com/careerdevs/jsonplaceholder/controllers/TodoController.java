package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.TodoModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final String todoEndPoint = "https://jsonplaceholder.typicode.com/todos";

    @GetMapping("/all")
    public ResponseEntity<?> getAllTodos (RestTemplate restTemplate) {
        try {
            TodoModel[] response = restTemplate.getForObject(todoEndPoint, TodoModel[].class);
            for (int i = 0; i < response.length; i++) {
                TodoModel user = response[i];
                System.out.println(user.isCompleted());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getTodoById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting user with ID " + id);

            String url = todoEndPoint + "/" + id;

            TodoModel response = restTemplate.getForObject(url, TodoModel.class);

            System.out.println(response);

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Todo Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteTodoById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting Todo With ID: " + id);

            String url = todoEndPoint + "/" + id;

            restTemplate.getForObject(url, TodoModel.class);

            restTemplate.delete(url);

            return ResponseEntity.ok("Todo with ID: " + id + " has been deleted.");

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Todo Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // POST localhost:8080/api/todos
    @PostMapping("/")
    public ResponseEntity<?> createNewTodo (RestTemplate restTemplate, @RequestBody TodoModel newTodo) {
        try {

            //TODO: User data validation

            TodoModel createdTodo = restTemplate.postForObject(todoEndPoint, newTodo, TodoModel.class);

            return ResponseEntity.ok(createdTodo);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // PUT localhost:8080/api/todos
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateTodo (RestTemplate restTemplate, @RequestBody TodoModel updateTodoData,
                                         @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            String url = todoEndPoint + "/" + id;

            //TODO: User data validation
            HttpEntity<TodoModel> reqEntity = new HttpEntity<>(updateTodoData);

//            restTemplate.put(url, updateTodoData, TodoModel.class);
            ResponseEntity<TodoModel> jphRes = restTemplate.exchange(url, HttpMethod.PUT, reqEntity, TodoModel.class);

            return ResponseEntity.ok(jphRes.getBody());

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Todo Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
