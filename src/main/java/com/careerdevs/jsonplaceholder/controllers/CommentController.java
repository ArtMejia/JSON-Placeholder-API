package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.CommentModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final String commentEndPoint = "https://jsonplaceholder.typicode.com/comments";

    @GetMapping("/all")
    public ResponseEntity<?> getAllComments (RestTemplate restTemplate) {
        CommentModel[] response = restTemplate.getForObject(commentEndPoint, CommentModel[].class);
        try {

            for (int i = 0; i < response.length; i++) {
                CommentModel user = response[i];
                System.out.println(user.getId());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCommentById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting comment with ID " + id);

            String url = commentEndPoint + "/" + id;

            CommentModel response = restTemplate.getForObject(url, CommentModel.class);

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Comment Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
}
