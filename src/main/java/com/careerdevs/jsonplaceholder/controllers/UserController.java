package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.UserModel;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping ("/api/users")
public class UserController {

    private final String usersEndpoint = "https://jsonplaceholder.typicode.com/users";

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers (RestTemplate restTemplate) {
        try {
            UserModel[] response = restTemplate.getForObject(usersEndpoint, UserModel[].class);

            for (int i = 0; i < response.length; i++) {
                UserModel user = response[i];
                System.out.println(user.getName());
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting user with ID " + id);

            String url = usersEndpoint + "/" + id;

            UserModel response = restTemplate.getForObject(url, UserModel.class);

            System.out.println(response.getName());

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("User Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting user with ID " + id);

            String url = usersEndpoint + "/" + id;

           restTemplate.getForObject(url, UserModel.class);

            restTemplate.delete(url);

            return ResponseEntity.ok("User with ID: " + id + " has been deleted");

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("User Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // POST localhost:8080/api/users
    @PostMapping("/")
    public ResponseEntity<?> createNewUser (RestTemplate restTemplate, @RequestBody UserModel newUser) {
        try {

            //TODO: User data validation

            String url = usersEndpoint;

            UserModel createdUser = restTemplate.postForObject(url, newUser, UserModel.class);

            return ResponseEntity.ok(createdUser);


            } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
            }

    }


    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateUser (RestTemplate restTemplate, @RequestBody UserModel updateUserData, @PathVariable String id) {
        try {
            Integer.parseInt(id);

            //TODO: User data validation

            String url = usersEndpoint + "/" + id;

            UserModel res = restTemplate.patchForObject(url, updateUserData, UserModel.class);

            return ResponseEntity.ok(res);

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("User Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}