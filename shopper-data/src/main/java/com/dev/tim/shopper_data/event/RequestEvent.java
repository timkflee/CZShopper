package com.dev.tim.shopper_data.event;

import com.dev.tim.shopper_rest.object.ShopItem;

import java.util.List;

/**
 * Created by Tim on 12/27/2015.
 */
public class RequestEvent {

    private List<ShopItem> items;

    public RequestEvent(List<ShopItem> items) {
        this.items = items;
    }

    public List<ShopItem> getItems() {
        return items;
    }
}
