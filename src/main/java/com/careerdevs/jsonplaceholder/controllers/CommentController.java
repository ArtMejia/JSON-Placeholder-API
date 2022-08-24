package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.CommentModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

            System.out.println("Getting Comment with ID: " + id);

            String url = commentEndPoint + "/" + id;

            CommentModel response = restTemplate.getForObject(url, CommentModel.class);

            System.out.println(response);

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

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteCommentById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting Post With ID: " + id);

            String url = commentEndPoint + "/" + id;

            restTemplate.getForObject(url, CommentModel.class);

            restTemplate.delete(url);

            return ResponseEntity.ok("Comment with ID: " + id + " has been deleted.");

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Post Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // POST localhost:8080/api/comments
    @PostMapping("/")
    public ResponseEntity<?> createNewComment (RestTemplate restTemplate, @RequestBody CommentModel newComment) {
        try {

            //TODO: User data validation

            CommentModel createdComment = restTemplate.postForObject(commentEndPoint, newComment, CommentModel.class);

            return ResponseEntity.ok(createdComment);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // PUT localhost:8080/api/comments
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateComment (RestTemplate restTemplate, @RequestBody CommentModel updateCommentData,
                                         @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            String url = commentEndPoint + "/" + id;

            //TODO: User data validation
            HttpEntity<CommentModel> reqEntity = new HttpEntity<>(updateCommentData);

//            restTemplate.put(url, updateCommentData, CommentModel.class);
            ResponseEntity<CommentModel> jphRes = restTemplate.exchange(url, HttpMethod.PUT, reqEntity, CommentModel.class);

            return ResponseEntity.ok(jphRes.getBody());

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
