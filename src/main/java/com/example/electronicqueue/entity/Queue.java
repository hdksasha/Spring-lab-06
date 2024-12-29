package com.example.electronicqueue.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "queue")
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_open", nullable = false)
    private boolean isOpen;

    @OneToMany(mappedBy = "queue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
