package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.PostModel;
import com.careerdevs.jsonplaceholder.models.UserModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping ("/api/posts")
public class PostController {

    private final String postsEndpoint = "https://jsonplaceholder.typicode.com/posts";

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts (RestTemplate restTemplate) {
        try {
            PostModel[] response = restTemplate.getForObject(postsEndpoint, PostModel[].class);
            for (int i = 0; i < response.length; i++) {
                PostModel user = response[i];
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
    public ResponseEntity<?> getPostById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting Post With ID: " + id);

            String url = postsEndpoint + "/" + id;

            PostModel response = restTemplate.getForObject(url, PostModel.class);

            System.out.println(response);

            return ResponseEntity.ok(response);

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

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deletePostById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting Post With ID: " + id);

            String url = postsEndpoint + "/" + id;

            restTemplate.getForObject(url, PostModel.class);

            restTemplate.delete(url);

            return ResponseEntity.ok("Post with ID: " + id + " has been deleted.");

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

    // POST localhost:8080/api/posts
    @PostMapping("/")
    public ResponseEntity<?> createNewPost (RestTemplate restTemplate, @RequestBody UserModel newPost) {
        try {

            //TODO: User data validation

            PostModel createdPost = restTemplate.postForObject(postsEndpoint, newPost, PostModel.class);

            return ResponseEntity.ok(createdPost);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // PUT localhost:8080/api/posts
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updatePost (RestTemplate restTemplate, @RequestBody PostModel updatePostData,
                                         @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            String url = postsEndpoint + "/" + id;

            //TODO: User data validation
            HttpEntity<PostModel> reqEntity = new HttpEntity<>(updatePostData);

//            restTemplate.put(url, updatePostData, PostModel.class);
            ResponseEntity<PostModel> jphRes = restTemplate.exchange(url, HttpMethod.PUT, reqEntity,PostModel.class);

            return ResponseEntity.ok(jphRes.getBody());

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
}
