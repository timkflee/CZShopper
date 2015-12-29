package com.dev.tim.shopper_data.util;

import com.dev.tim.shopper_rest.object.ShopItem;

import java.util.Comparator;

/**
 * Created by Tim on 12/27/2015.
 */
public class ItemSort implements Comparator<ShopItem> {
    @Override
    public int compare(ShopItem lhs, ShopItem rhs) {
        if (lhs.getCategory().equals(rhs.getCategory())) {
            return lhs.getName().compareTo(rhs.getName());
        }
        return lhs.getCategory().compareTo(rhs.getCategory());
    }
}
