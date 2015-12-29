package com.dev.tim.shopper_data.repositories;

import com.dev.tim.shopper_data.DBOpenHelper;
import com.dev.tim.shopper_data.greendao.ItemDb;
import com.dev.tim.shopper_data.greendao.ItemDbDao;
import com.dev.tim.shopper_rest.object.ShopItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 12/28/2015.
 */
public class ItemRepository {

    private DBOpenHelper manager;

    public ItemRepository() {
        manager = DBOpenHelper.getInstance();
    }

    public List<ShopItem> getAllItems() {
        ItemDbDao dao = manager.getItemDbDao();

        // Query with Sort
        return convertToShopItems(dao.queryBuilder().orderAsc(ItemDbDao.Properties.Category, ItemDbDao.Properties.Name).list());
    }

    public void insertItems(ShopItem item) {
        ItemDbDao dao = manager.getItemDbDao();
        ItemDb current = new ItemDb();
        current.setId(item.getId());
        current.setCategory(item.getCategory());
        current.setName(item.getName());
        current.setCreated_at(item.getCreatedAt());
        current.setUpdated_at(item.getUpdatedAt());

        dao.insert(current);
    }

    public void updateItem(ShopItem item) {
        ItemDbDao dao = manager.getItemDbDao();
        ItemDb current = new ItemDb();
        current.setId(item.getId());
        current.setCategory(item.getCategory());
        current.setName(item.getName());
        current.setCreated_at(item.getCreatedAt());
        current.setUpdated_at(item.getUpdatedAt());

        dao.update(current);
    }

    public void deleteItem(long id) {
        ItemDbDao dao = manager.getItemDbDao();
        dao.deleteByKey(id);
    }

    public void insertItems(List<ShopItem> items) {
        ItemDbDao dao = manager.getItemDbDao();
        dao.insertOrReplaceInTx(convertToItemDbs(items));
    }

    public void clear() {
        ItemDbDao dao = manager.getItemDbDao();
        dao.deleteAll();
    }

    public static List<ItemDb> convertToItemDbs(List<ShopItem> items) {
        List<ItemDb> newItems = new ArrayList<>();

        for (ShopItem item : items) {
            ItemDb current = new ItemDb();
            current.setId(item.getId());
            current.setCategory(item.getCategory());
            current.setName(item.getName());
            current.setCreated_at(item.getCreatedAt());
            current.setUpdated_at(item.getUpdatedAt());
            newItems.add(current);
        }

        return newItems;
    }

    public static List<ShopItem> convertToShopItems(List<ItemDb> items) {
        List<ShopItem> newItems = new ArrayList<>();

        for (ItemDb item : items) {
            ShopItem current = new ShopItem();
            current.setId(item.getId());
            current.setCategory(item.getCategory());
            current.setName(item.getName());
            current.setCreatedAt(item.getCreated_at());
            current.setUpdatedAt(item.getUpdated_at());
            newItems.add(current);
        }

        return newItems;
    }
}
