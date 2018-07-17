package io.monteirodev.comfreyproject.data;

public class Plant {
    private int id;
    private String name;
    private String imageUrl;
    private String expertName;
    private String expertPictureUrl;
    private String information;

    public Plant(int id, String name, String imageUrl, String expertName, String expertPictureUrl, String information) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.expertName = expertName;
        this.expertPictureUrl = expertPictureUrl;
        this.information = information;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getExpertName() {
        return expertName;
    }

    public String getExpertPictureUrl() {
        return expertPictureUrl;
    }

    public String getInformation() {
        return information;
    }
}
