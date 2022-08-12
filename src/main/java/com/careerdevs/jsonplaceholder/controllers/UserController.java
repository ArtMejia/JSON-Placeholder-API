package com.careerdevs.jsonplaceholder.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping ("/api/users")
public class UserController {
    private final String jsonPlaceholderEndpoint = "https://jsonplaceholder.typicode.com/posts";
    private final String commentEndPoint = "https://jsonplaceholder.typicode.com/comments";
    private final String photosEndPoint = "https://jsonplaceholder.typicode.com/photos";
    private final String albumsEndPoint = "https://jsonplaceholder.typicode.com/albums";
    private final String usersEndpoint = "https://jsonplaceholder.typicode.com/users";

    @GetMapping("/all")
    public ResponseEntity<?> getUsers (RestTemplate restTemplate) {
        String response = restTemplate.getForObject(usersEndpoint, String.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/comments")
    public Object commentsRoute(RestTemplate restTemplate) {
        return restTemplate.getForObject(commentEndPoint, Object.class);

    }

    @GetMapping("/photos")
    public Object photosRoute(RestTemplate restTemplate) {
        return restTemplate.getForObject(photosEndPoint, Object.class);
    }

    @GetMapping("/albums")
    public Object albumsRoute(RestTemplate restTemplate) {
        return restTemplate.getForObject(albumsEndPoint, Object.class);
    }

}