package com.example.quanlychitieu;

import java.io.Serializable;

public class Expense implements Serializable {
    private String id;
    private String title;
    private double amount;
    private String category;
    private String date;
    private String note;

    public Expense(String id, String title, double amount, String category, String date, String note) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
    public String getNote() { return note; }
}