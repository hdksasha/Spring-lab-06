package com.example.electronicqueue.model;

public class Place {
    private int id;          // Primary key
    private int queueId;     // Зовнішній ключ на Queue
    private int position;    // Позиція в черзі
    private String userName; // Ім'я користувача

    // Getters і Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
