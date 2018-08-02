package io.monteirodev.comfreyproject.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

@Entity(tableName = "plants")
public class Plant implements Parcelable {
    @PrimaryKey
    private int id;
    private String name;
    private String imageUrl;
    private int recipeId;
    @Ignore
    private ArrayList<PlantDetail> details;

    public Plant(int id, String name, String imageUrl, int recipeId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.recipeId = recipeId;
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

    public int getRecipeId() {
        return recipeId;
    }

    public ArrayList<PlantDetail> getDetails() {
        return details;
    }

    protected Plant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        recipeId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeInt(recipeId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Plant> CREATOR = new Creator<Plant>() {
        @Override
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };
}
