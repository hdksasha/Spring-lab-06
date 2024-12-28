package com.example.electronicqueue.controller;

import com.example.electronicqueue.model.Place;
import com.example.electronicqueue.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public ResponseEntity<Integer> addPlace(@RequestParam int queueId, @RequestParam int position, @RequestParam String userName) {
        int id = placeService.addPlace(queueId, position, userName);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Place>> getAllPlaces() {
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    @GetMapping("/queue/{queueId}")
    public ResponseEntity<List<Place>> getPlacesByQueueId(@PathVariable int queueId) {
        return ResponseEntity.ok(placeService.getPlacesByQueueId(queueId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable int id) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}
