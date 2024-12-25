package com.example.electronicqueue.service;

import com.example.electronicqueue.repository.QueueRepository;
import com.example.electronicqueue.model.*;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;


@Service
public class QueueService {
    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public Queue createQueue(String name) {
        Queue queue = new Queue();
        queue.setId(UUID.randomUUID().toString());
        queue.setName(name);
        return queueRepository.save(queue);
    }

    public Optional<Queue> getQueue(String id) {
        return queueRepository.findById(id);
    }

    // Видалення черги
    public boolean deleteQueue(String id) {
        return queueRepository.delete(id);
    }

    // Додавання користувача до черги
    public void addUserToQueue(String queueId, String userName) {
        Queue queue = queueRepository.findById(queueId).orElseThrow();
        if (!queue.isOpen()) {
            throw new IllegalStateException("Черга закрита для запису.");
        }

        Place place = new Place();
        place.setPosition(queue.getPlaces().size() + 1);
        place.setUserName(userName);
        queue.getPlaces().add(place);
    }

    // Перегляд місця у черзі
    public int getUserPosition(String queueId, String userName) {
        Queue queue = queueRepository.findById(queueId).orElseThrow();
        return queue.getPlaces().stream()
                .filter(p -> p.getUserName().equals(userName))
                .findFirst()
                .map(Place::getPosition)
                .orElse(-1);
    }

    // Видалення користувача з голови черги
    public void moveToNext(String queueId) {
        Queue queue = queueRepository.findById(queueId).orElseThrow();
        if (!queue.getPlaces().isEmpty()) {
            queue.getPlaces().remove(0);
        }
    }

    // Видалення конкретного користувача з черги
    public void removeUser(String queueId, String userName) {
        Queue queue = queueRepository.findById(queueId).orElseThrow();
        queue.getPlaces().removeIf(p -> p.getUserName().equals(userName));
    }

    // Закриття черги
    public void closeQueue(String queueId) {
        Queue queue = queueRepository.findById(queueId).orElseThrow();
        queue.setOpen(false);
    }

    public List<Queue> getAllQueues() {
        return new ArrayList<>(queueRepository.findAll());
    }

    public Optional<Queue> getQueueById(String id) {
        return queueRepository.findById(id);
    }

    public Optional<Queue> updateQueue(String id, String newName) {
        Optional<Queue> queue = queueRepository.findById(id);
        queue.ifPresent(q -> {
            q.setName(newName);
            queueRepository.save(q);
        });
        return queue;
    }

    public List<Queue> filterQueuesByName(String name) {
        return queueRepository.findAll().stream()
                .filter(queue -> queue.getName().contains(name))
                .toList();
    }

    // Pagination
    public List<Queue> getQueuesByPage(int page, int size) {
        List<Queue> allQueues = new ArrayList<>(queueRepository.findAll());
        int fromIndex = Math.min(page * size, allQueues.size());
        int toIndex = Math.min(fromIndex + size, allQueues.size());
        return allQueues.subList(fromIndex, toIndex);
    }

}
