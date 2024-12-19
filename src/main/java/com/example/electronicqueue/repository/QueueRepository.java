package com.example.electronicqueue.repository;

import com.example.electronicqueue.model.Queue;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class QueueRepository {
    private final Map<String, Queue> queues = new HashMap<>();

    public Queue save(Queue queue) {
        queues.put(queue.getId(), queue);
        return queue;
    }

    public Optional<Queue> findById(String id) {
        return Optional.ofNullable(queues.get(id));
    }

    public void delete(String id) {
        queues.remove(id);
    }
}
