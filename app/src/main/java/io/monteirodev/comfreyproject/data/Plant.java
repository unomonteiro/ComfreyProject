package io.monteirodev.comfreyproject.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Plant implements Parcelable {
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

    protected Plant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        expertName = in.readString();
        expertPictureUrl = in.readString();
        information = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(expertName);
        dest.writeString(expertPictureUrl);
        dest.writeString(information);
    }
}