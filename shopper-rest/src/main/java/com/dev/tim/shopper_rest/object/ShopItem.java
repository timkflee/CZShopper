package com.dev.tim.shopper_rest.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Comparator;

/**
 * Created by Tim on 12/27/2015.
 */
@JsonObject
public class ShopItem implements Parcelable {

    @JsonField
    private String category;

    @JsonField(name = "created_at")
    private String createdAt;

    @JsonField
    private long id;

    @JsonField
    private String name;

    @JsonField(name = "updated_at")
    private String updatedAt;

    @JsonField(name = "user_id")
    private long userId;

    public ShopItem(){
    }

    public ShopItem(String category, String createdAt, long id, String name, String updatedAt, long userId) {
        this.category = category;
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }


    protected ShopItem(Parcel in) {
        category = in.readString();
        createdAt = in.readString();
        id = in.readLong();
        name = in.readString();
        updatedAt = in.readString();
        userId = in.readLong();
    }

    public static final Creator<ShopItem> CREATOR = new Creator<ShopItem>() {
        @Override
        public ShopItem createFromParcel(Parcel in) {
            return new ShopItem(in);
        }

        @Override
        public ShopItem[] newArray(int size) {
            return new ShopItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(createdAt);
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(updatedAt);
        dest.writeLong(userId);
    }

    public static Creator<ShopItem> getCREATOR() {
        return CREATOR;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
