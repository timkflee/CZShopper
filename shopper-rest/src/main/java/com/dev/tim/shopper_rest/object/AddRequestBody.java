package com.dev.tim.shopper_rest.object;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Tim on 12/27/2015.
 */
@JsonObject
public class AddRequestBody {

    @JsonField
    private AddItem item;

    public AddRequestBody() {
    }

    public AddRequestBody(String name, String category) {
        this.item = new AddItem(name, category);
    }

    public AddItem getItem() {
        return item;
    }

    public void setItem(AddItem item) {
        this.item = item;
    }
}
