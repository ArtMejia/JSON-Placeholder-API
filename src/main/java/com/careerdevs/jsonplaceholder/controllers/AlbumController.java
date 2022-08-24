package com.careerdevs.jsonplaceholder.controllers;

import com.careerdevs.jsonplaceholder.models.AlbumModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final String albumsEndPoint = "https://jsonplaceholder.typicode.com/albums";

    @GetMapping("/all")
    public ResponseEntity<?> getAllAlbums (RestTemplate restTemplate) {
        try {
            AlbumModel[] response = restTemplate.getForObject(albumsEndPoint, AlbumModel[].class);
            for (int i = 0; i < response.length; i++) {
                AlbumModel user = response[i];
                System.out.println(user.getTitle());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAlbumById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting Album with ID: " + id);

            String url = albumsEndPoint + "/" + id;

            AlbumModel response = restTemplate.getForObject(url, AlbumModel.class);

            System.out.println(response);

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Album Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteAlbumById (RestTemplate restTemplate, @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            System.out.println("Getting Album With ID: " + id);

            String url = albumsEndPoint + "/" + id;

            restTemplate.getForObject(url, AlbumModel.class);

            restTemplate.delete(url);

            return ResponseEntity.ok("Album with ID: " + id + " has been deleted.");

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Album Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // POST localhost:8080/api/albums
    @PostMapping("/")
    public ResponseEntity<?> createNewAlbum (RestTemplate restTemplate, @RequestBody AlbumModel newAlbum) {
        try {

            //TODO: User data validation

            AlbumModel createdAlbum = restTemplate.postForObject(albumsEndPoint, newAlbum, AlbumModel.class);

            return ResponseEntity.ok(createdAlbum);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // PUT localhost:8080/api/albums
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateAlbum (RestTemplate restTemplate, @RequestBody AlbumModel updateAlbumData,
                                         @PathVariable String id) {
        try {

            // throws NumberFormatException if id is not an int
            Integer.parseInt(id);

            String url = albumsEndPoint + "/" + id;

            //TODO: User data validation
            HttpEntity<AlbumModel> reqEntity = new HttpEntity<>(updateAlbumData);

//            restTemplate.put(url, updateAlbumData, AlbumModel.class);
            ResponseEntity<AlbumModel> jphRes = restTemplate.exchange(url, HttpMethod.PUT, reqEntity, AlbumModel.class);

            return ResponseEntity.ok(jphRes.getBody());

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("ID " + id + ", is not a valid ID. Must be a whole number");

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(404).body("Album Not Found With ID: " + id);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
