package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.PhotoModel;
import com.careerdevs.jsonplaceholder.models.PostModel;
import com.careerdevs.jsonplaceholder.models.UserModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

            System.out.println("Getting Photo With ID: " + id);

            String url = photosEndPoint + "/" + id;

            PhotoModel response = restTemplate.getForObject(url, PhotoModel.class);

            System.out.println(response);

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

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deletePhotoById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting Photo With ID: " + id);

            String url = photosEndPoint + "/" + id;

            restTemplate.getForObject(url, PostModel.class);

            restTemplate.delete(url);

            return ResponseEntity.ok("Photo with ID: " + id + " has been deleted.");

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

    // POST localhost:8080/api/photos
    @PostMapping("/")
    public ResponseEntity<?> createNewPhoto (RestTemplate restTemplate, @RequestBody PhotoModel newPhoto) {
        try {

            //TODO: User data validation

            PhotoModel createdPhoto = restTemplate.postForObject(photosEndPoint, newPhoto, PhotoModel.class);

            return ResponseEntity.ok(createdPhoto);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // PUT localhost:8080/api/photos
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updatePhoto (RestTemplate restTemplate, @RequestBody PhotoModel updatePhotoData,
                                         @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            String url = photosEndPoint + "/" + id;

            //TODO: User data validation
            HttpEntity<PhotoModel> reqEntity = new HttpEntity<>(updatePhotoData);

//            restTemplate.put(url, updatePhotoData, PhotoModel.class);
            ResponseEntity<PhotoModel> jphRes = restTemplate.exchange(url, HttpMethod.PUT, reqEntity, PhotoModel.class);

            return ResponseEntity.ok(jphRes.getBody());

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
