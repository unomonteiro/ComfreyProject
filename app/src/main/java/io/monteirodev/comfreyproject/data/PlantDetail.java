package io.monteirodev.comfreyproject.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "plantDetails",
        foreignKeys = {@ForeignKey(entity = Plant.class,
                parentColumns = "id",
                childColumns = "plantId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "plantId")})
public class PlantDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int plantId;
    private String title;
    private String body;
    private String imageUrl;

    public PlantDetail(int id, int plantId, String title, String body, String imageUrl) {
        this.id = id;
        this.plantId = plantId;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public int getPlantId() {
        return plantId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }
}
