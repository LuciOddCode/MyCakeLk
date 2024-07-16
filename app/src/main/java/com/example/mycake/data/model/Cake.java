package com.example.mycake.data.model;

public class Cake {
    private int id;
    private String name;
    private String price;
    private String description;
    private String image;
    private String category;

    public Cake(int id, String name, String price, String description, String image, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }
}
