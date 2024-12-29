package com.example.electronicqueue.repository;

import com.example.electronicqueue.entity.Queue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends CrudRepository<Queue, Integer> {
    @Query("SELECT q FROM Queue q WHERE q.isOpen = ?1")
    List<Queue> findByStatus(boolean isOpen);

    List<Queue> findByNameContaining(String name);
}
