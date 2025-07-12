package com.example.unicornshoppinglist;

public class Note {
    private int id;
    private String text;
    private String color;

    public Note(int id, String text, String color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }
}