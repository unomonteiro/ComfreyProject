package io.monteirodev.comfreyproject.data;

public class MyMenuOption {
    public static final String PLANTS = "plants_key";
    public static final String RECIPES = "recipes_key";
    public static final String GET_INVOLVED = "get_involved_key";
    public static final String ABOUT = "about_key";

    private String key;
    private String name;
    private int imageId;

    public MyMenuOption(String key, String name, int imageId) {
        this.key = key;
        this.name = name;
        this.imageId = imageId;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
