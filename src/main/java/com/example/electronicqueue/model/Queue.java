package com.example.electronicqueue.model;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private String id;
    private String name;
    private boolean isOpen = true; // Черга відкрита для запису
    private List<Place> places = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Place> getPlaces() {
        return places;
    }

    public boolean arePlaces() {
        return !places.isEmpty();
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}

