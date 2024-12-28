package com.example.electronicqueue.repository;

import com.example.electronicqueue.model.Place;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaceRepository {
    private final JdbcTemplate jdbcTemplate;

    public PlaceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Додавання місця
    public int addPlace(int queueId, int position, String userName) {
        String sql = "INSERT INTO place (queue_id, position, user_name) VALUES (?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class, queueId, position, userName);
    }

    // Отримання всіх місць
    public List<Place> getAllPlaces() {
        String sql = "SELECT * FROM place";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Place place = new Place();
            place.setId(rs.getInt("id"));
            place.setQueueId(rs.getInt("queue_id"));
            place.setPosition(rs.getInt("position"));
            place.setUserName(rs.getString("user_name"));
            return place;
        });
    }

    // Отримання місць для черги
    public List<Place> getPlacesByQueueId(int queueId) {
        String sql = "SELECT * FROM place WHERE queue_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Place place = new Place();
            place.setId(rs.getInt("id"));
            place.setQueueId(rs.getInt("queue_id"));
            place.setPosition(rs.getInt("position"));
            place.setUserName(rs.getString("user_name"));
            return place;
        }, queueId);
    }

    // Видалення місця
    public int deletePlace(int id) {
        String sql = "DELETE FROM place WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int deletePlacesByQueueId(int queueId) {
        String sql = "DELETE FROM place WHERE queue_id = ?";
        return jdbcTemplate.update(sql, queueId);
    }

}
