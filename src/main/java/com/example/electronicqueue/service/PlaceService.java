package com.example.electronicqueue.service;

import com.example.electronicqueue.entity.Place;
import com.example.electronicqueue.entity.Queue;
import com.example.electronicqueue.repository.PlaceRepository;
import com.example.electronicqueue.repository.QueueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final QueueRepository queueRepository;

    public PlaceService(PlaceRepository placeRepository, QueueRepository queueRepository) {
        this.placeRepository = placeRepository;
        this.queueRepository = queueRepository;
    }

    public Place addUserToQueue(Integer queueId, String userName) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new IllegalArgumentException("Queue not found"));
        Place place = new Place();
        place.setQueue(queue);
        place.setPosition((int) (placeRepository.countByQueueId(queueId) + 1));
        place.setUserName(userName);
        return placeRepository.save(place);
    }

    public List<Place> getPlacesInQueue(Integer queueId) {
        return placeRepository.findByQueueIdOrderByPositionAsc(queueId);
    }

    public boolean removeUserFromQueue(Integer queueId, Integer placeId) {
        if (placeRepository.existsByIdAndQueueId(placeId, queueId)) {
            placeRepository.deleteById(placeId);
            return true;
        }
        return false;
    }
}
