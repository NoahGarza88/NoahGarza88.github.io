package com.example.nlg_project2;

public class DailyWeightEntry {
    private int id;
    private float weight;
    private String date;

    // Constructors, getters, and setters
    public DailyWeightEntry() {
    }

    public DailyWeightEntry(int id, float weight, String date) {
        this.id = id;
        this.weight = weight;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
