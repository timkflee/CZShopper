package com.dev.tim.shopper_rest.object;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Tim on 12/27/2015.
 */
@JsonObject
public class AddItem {

    @JsonField
    private String name;

    @JsonField
    private String category;

    public AddItem() {
    }

    public AddItem(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
