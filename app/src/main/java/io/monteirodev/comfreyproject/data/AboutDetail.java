package io.monteirodev.comfreyproject.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "AboutDetails")
public class AboutDetail implements Detail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String body;
    private String imageUrl;

    public AboutDetail(int id, String title, String body, String imageUrl) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
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
}
