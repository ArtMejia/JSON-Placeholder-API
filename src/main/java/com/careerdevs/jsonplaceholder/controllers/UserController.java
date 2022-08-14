package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
                System.out.println("Lat: " + user.getAddress().getGeo().getLat());
                System.out.println("Lng: " + user.getAddress().getGeo().getLng() + "\n");
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

}