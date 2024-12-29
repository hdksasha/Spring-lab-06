package com.example.electronicqueue.repository;

import com.example.electronicqueue.entity.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Integer> {
    List<Place> findByQueueIdOrderByPositionAsc(Integer queueId);

    long countByQueueId(Integer queueId);

    boolean existsByIdAndQueueId(Integer id, Integer queueId);
}
