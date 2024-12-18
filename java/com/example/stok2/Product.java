package com.example.stok2;


public class Product {
    private int id;
    private String name;
    private String description;
    private int stock;
    private int stockAmount;

    public Product(int id, String name, String description, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.stockAmount = stockAmount;
    }
    public int getStockAmount() {
        return stockAmount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }
}

