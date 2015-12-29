package com.dev.tim.shopper_data.event;

import com.dev.tim.shopper_rest.object.ShopItem;

/**
 * Created by Tim on 12/27/2015.
 */
public class ItemEvent {
    private ShopItem item;

    public ItemEvent(ShopItem item) {
        this.item = item;
    }

    public ShopItem getItem() {
        return item;
    }
}
