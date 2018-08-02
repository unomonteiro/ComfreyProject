package io.monteirodev.comfreyproject.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipeDetails",
        foreignKeys = {@ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "recipeId")})
public class RecipeDetail implements Detail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int recipeId;
    private String title;
    private String body;
    private String imageUrl;

    public RecipeDetail(int id, int recipeId, String title, String body, String imageUrl) {
        this.id = id;
        this.recipeId = recipeId;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipeId;
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

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
