package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.PhotoModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final String photosEndPoint = "https://jsonplaceholder.typicode.com/photos";

    @GetMapping("/all")
    public ResponseEntity<?> getAllPhotos (RestTemplate restTemplate) {
        try {
            PhotoModel[] response = restTemplate.getForObject(photosEndPoint, PhotoModel[].class);

            for (int i = 0; i < response.length; i++) {
                PhotoModel user = response[i];
                System.out.println(user.getUrl());
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPhotoById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting photo with ID " + id);

            String url = photosEndPoint + "/" + id;

            PhotoModel response = restTemplate.getForObject(url, PhotoModel.class);

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Photo Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
