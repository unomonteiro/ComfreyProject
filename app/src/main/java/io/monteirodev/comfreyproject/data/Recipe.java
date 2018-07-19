package io.monteirodev.comfreyproject.data;

public class Recipe {
    int id;
    String name;
    String image;

    public Recipe(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
