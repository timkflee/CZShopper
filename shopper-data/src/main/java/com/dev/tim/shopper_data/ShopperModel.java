package com.dev.tim.shopper_data;

import android.content.Context;

import com.dev.tim.shopper_data.event.DeleteSuccessEvent;
import com.dev.tim.shopper_data.event.EditSuccessEvent;
import com.dev.tim.shopper_data.event.FailureEvent;
import com.dev.tim.shopper_data.event.ItemCreatedEvent;
import com.dev.tim.shopper_data.event.RequestEvent;
import com.dev.tim.shopper_data.repositories.ItemRepository;
import com.dev.tim.shopper_data.util.ItemSort;
import com.dev.tim.shopper_rest.ShopperAPI;
import com.dev.tim.shopper_rest.object.AddRequestBody;
import com.dev.tim.shopper_rest.object.ShopItem;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Response;

/**
 * Created by Tim on 12/27/2015.
 */
public class ShopperModel {

    private Context context;
    private ShopperAPI mApi;
    private EventBus bus;
    private ExecutorService mExecutor;
    private ItemRepository itemRepository;
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;

    public ShopperModel(Context context, ShopperAPI api, EventBus bus) {
        this.context = context;
        this.mApi = api;
        this.bus = bus;
        mExecutor = Executors.newFixedThreadPool(5);
        itemRepository = new ItemRepository();
    }

    private class ShopRequestRunnable implements Runnable {
        @Override
        public void run() {

            // Show Local Data
            List<ShopItem> localItems = itemRepository.getAllItems();
            if (!localItems.isEmpty())
                bus.post(new RequestEvent(localItems));

            // Request for Server Data
            ShopperModel.this.getServerData();
        }
    }

    private class ShopServerRequestRunnable implements Runnable {
        @Override
        public void run() {
            itemRepository.clear();

            // Request for Server Data
            ShopperModel.this.getServerData();
        }
    }

    private void getServerData() {
        Call<List<ShopItem>> call = mApi.getAllItems();
        try {
            Response<List<ShopItem>> response = call.execute();

            if (response != null && response.isSuccess()) {
                List<ShopItem> items = response.body();

                // Store to database
                itemRepository.insertItems(items);

                // Sort by category
                Collections.sort(items, new ItemSort());

                bus.post(new RequestEvent(response.body()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get items from database and server
     */
    public void getAllItems() {
        mExecutor.execute(new ShopRequestRunnable());
    }

    /**
     * Get items from server
     */
    public void getServerItems() {
        mExecutor.execute(new ShopServerRequestRunnable());
    }


    /**
     * Add Item Task
     */
    private class ItemAddRequestRunnable implements Runnable {

        private AddRequestBody body;

        public ItemAddRequestRunnable(String name, String category) {
            body = new AddRequestBody(name, category);
        }

        @Override
        public void run() {
            Call<ShopItem> call = mApi.addItem(body);
            try {
                Response<ShopItem> response = call.execute();
                if (response != null && response.code() == HTTP_CREATED) {

                    // Add to database
                    itemRepository.insertItems(response.body());

                    bus.post(new ItemCreatedEvent());
                } else {
                    bus.post(new FailureEvent(context.getString(R.string.item_create_failed)));
                }
            } catch (IOException e) {
                bus.post(new FailureEvent(e.getLocalizedMessage()));
                e.printStackTrace();
            }
        }
    }

    public void addItem(String name, String category) {
        mExecutor.execute(new ItemAddRequestRunnable(name, category));
    }


    /**
     * Edit Item Task
     */
    private class ItemEditRequestRunnable implements Runnable {
        private AddRequestBody body;
        private long id;

        public ItemEditRequestRunnable(long id, String name, String category) {
            this.id = id;
            this.body = new AddRequestBody(name, category);
        }

        @Override
        public void run() {
            Call<ShopItem> call = mApi.updateItem(id, body);
            try {
                Response<ShopItem> response = call.execute();

                if (response != null && response.code() == HTTP_OK) {
                    itemRepository.updateItem(response.body());
                    bus.post(new EditSuccessEvent());
                } else
                    bus.post(new FailureEvent(context.getString(R.string.item_update_failed)));
            } catch (IOException e) {
                bus.post(new FailureEvent(e.getLocalizedMessage()));
                e.printStackTrace();
            }

        }
    }

    public void updateItem(long id, String name, String category) {
        mExecutor.execute(new ItemEditRequestRunnable(id, name, category));
    }

    /**
     * Delete Item Task
     */
    private class ItemDeleteRequestRunnable implements Runnable {

        private long id;
        public ItemDeleteRequestRunnable(long id) {
            this.id = id;
        }

        @Override
        public void run() {
            Call<Void> call = mApi.deleteItem(id);
            try {
                Response response = call.execute();
                if (response != null && response.code() == HTTP_OK) {
                    itemRepository.deleteItem(id);
                    bus.post(new DeleteSuccessEvent());
                } else {
                    bus.post(new FailureEvent(context.getString(R.string.item_delete_failed)));
                }
            } catch (IOException e) {
                bus.post(new FailureEvent(e.getLocalizedMessage()));
                e.printStackTrace();
            }
        }
    }

    public void deleteItem(long id) {
        mExecutor.execute(new ItemDeleteRequestRunnable(id));
    }
}
