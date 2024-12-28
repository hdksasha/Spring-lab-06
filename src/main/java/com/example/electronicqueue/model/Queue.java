package com.example.electronicqueue.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class Queue {
    @NotNull
    private int id;         // Primary key

    @NotNull
    @Size(min = 1, max = 255)
    private String name;    // Назва черги

    private boolean isOpen = true;

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
