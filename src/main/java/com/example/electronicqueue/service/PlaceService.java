package com.example.electronicqueue.service;

import com.example.electronicqueue.model.Place;
import com.example.electronicqueue.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public int addPlace(int queueId, int position, String userName) {
        return placeRepository.addPlace(queueId, position, userName);
    }

    public List<Place> getAllPlaces() {
        return placeRepository.getAllPlaces();
    }

    public List<Place> getPlacesByQueueId(int queueId) {
        return placeRepository.getPlacesByQueueId(queueId);
    }

    public int deletePlace(int id) {
        return placeRepository.deletePlace(id);
    }
}
