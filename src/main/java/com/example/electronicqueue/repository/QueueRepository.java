package com.example.electronicqueue.repository;

import com.example.electronicqueue.model.Queue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueueRepository {
    private final JdbcTemplate jdbcTemplate;

    public QueueRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Створення черги
    public int createQueue(String name, boolean isOpen) {
        String sql = "INSERT INTO queue (name, is_open) VALUES (?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class, name, isOpen);
    }

    // Отримання всіх черг
    public List<Queue> getAllQueues() {
        String sql = "SELECT * FROM queue";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Queue queue = new Queue();
            queue.setId(rs.getInt("id"));
            queue.setName(rs.getString("name"));
            queue.setOpen(rs.getBoolean("is_open"));
            return queue;
        });
    }

    // Отримання черги за ID
    public Queue getQueueById(int id) {
        String sql = "SELECT * FROM queue WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Queue queue = new Queue();
            queue.setId(rs.getInt("id"));
            queue.setName(rs.getString("name"));
            queue.setOpen(rs.getBoolean("is_open"));
            return queue;
        }, id);
    }

    // Оновлення черги
    public int updateQueue(int id, String name, boolean isOpen) {
        String sql = "UPDATE queue SET name = ?, is_open = ? WHERE id = ?";
        return jdbcTemplate.update(sql, name, isOpen, id);
    }

    // Видалення черги
    public int deleteQueue(int id) {
        String sql = "DELETE FROM queue WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Queue> findQueuesByStatus(boolean isOpen) {
        String sql = "SELECT * FROM queue WHERE is_open = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Queue queue = new Queue();
            queue.setId(rs.getInt("id"));
            queue.setName(rs.getString("name"));
            queue.setOpen(rs.getBoolean("is_open"));
            return queue;
        }, isOpen);
    }

}
