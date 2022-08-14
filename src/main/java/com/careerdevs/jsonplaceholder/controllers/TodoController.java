package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.TodoModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/todo")
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
}
