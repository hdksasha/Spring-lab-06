package com.example.electronicqueue.service;

import com.example.electronicqueue.entity.Queue;
import com.example.electronicqueue.repository.QueueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueueService {
    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public Queue createQueue(String name, boolean isOpen) {
        Queue queue = new Queue();
        queue.setName(name);
        queue.setOpen(isOpen);
        return queueRepository.save(queue);
    }

    public List<Queue> getAllQueues() {
        return (List<Queue>) queueRepository.findAll();
    }

    public Optional<Queue> getQueueById(int id) {
        return queueRepository.findById(id);
    }

    public List<Queue> getQueuesByStatus(boolean isOpen) {
        return queueRepository.findByStatus(isOpen);
    }

    public Optional<Queue> updateQueue(Integer id, String name, boolean isOpen) {
        return queueRepository.findById(id).map(queue -> {
            queue.setName(name);
            queue.setOpen(isOpen);
            return queueRepository.save(queue);
        });
    }

    public boolean deleteQueue(Integer id) {
        if (queueRepository.existsById(id)) {
            queueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Queue> searchQueuesByName(String name) {
        return queueRepository.findByNameContaining(name);
    }

}
