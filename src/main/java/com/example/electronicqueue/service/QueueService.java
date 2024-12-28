package com.example.electronicqueue.service;

import com.example.electronicqueue.model.Queue;
import com.example.electronicqueue.repository.PlaceRepository;
import com.example.electronicqueue.repository.QueueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QueueService {
    private final QueueRepository queueRepository;
    private final PlaceRepository placeRepository;

    public QueueService(QueueRepository queueRepository, PlaceRepository placeRepository) {
        this.queueRepository = queueRepository;
        this.placeRepository = placeRepository;
    }

    // Повертає ID черги для RESTful API
    public int createQueue(String name, boolean isOpen) {
        return queueRepository.createQueue(name, isOpen);
    }

    // Повертає об'єкт Queue для веб-інтерфейсу
    public Queue createQueueAndReturn(String name, boolean isOpen) {
        int id = createQueue(name, isOpen);
        return queueRepository.getQueueById(id);
    }

    public List<Queue> getAllQueues() {
        return queueRepository.getAllQueues();
    }

    public Queue getQueueById(int id) {
        return queueRepository.getQueueById(id);
    }

    // Додавання користувача до черги
    public void addUserToQueue(int queueId, String userName) {
        int position = placeRepository.getPlacesByQueueId(queueId).size() + 1;
        placeRepository.addPlace(queueId, position, userName);
    }

    // Видалення користувача з черги
    public void removeUserFromQueue(int queueId, String userName) {
        placeRepository.getPlacesByQueueId(queueId).stream()
                .filter(place -> place.getUserName().equals(userName))
                .findFirst()
                .ifPresent(place -> placeRepository.deletePlace(place.getId()));
    }

    // Перемістити до наступного користувача
    public void moveToNext(int queueId) {
        placeRepository.getPlacesByQueueId(queueId).stream()
                .min((p1, p2) -> Integer.compare(p1.getPosition(), p2.getPosition()))
                .ifPresent(place -> placeRepository.deletePlace(place.getId()));
    }

    public void closeQueue(int queueId) {
        Queue queue = getQueueById(queueId);
        queue.setOpen(false);
        queueRepository.updateQueue(queue.getId(), queue.getName(), false);
    }

    public int updateQueue(int id, String name, boolean isOpen) {
        return queueRepository.updateQueue(id, name, isOpen);
    }

    public int deleteQueue(int id) {
        return queueRepository.deleteQueue(id);
    }

    @Transactional
    public void deleteQueueAndPlaces(int queueId) {
        placeRepository.deletePlacesByQueueId(queueId);
        queueRepository.deleteQueue(queueId);
    }

    public List<Queue> filterQueuesByStatus(boolean isOpen) {
        return queueRepository.findQueuesByStatus(isOpen);
    }

}
