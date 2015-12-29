package com.dev.tim.shopper_rest;

import com.dev.tim.shopper_rest.object.AddRequestBody;
import com.dev.tim.shopper_rest.object.ShopItem;
import com.squareup.okhttp.Response;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Tim on 12/27/2015.
 */
public interface ShopperAPI {

    @Headers("Accept: application/json")
    @GET(Paths.ITEMS_PATH)
    Call<List<ShopItem>> getAllItems();

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST(Paths.ITEMS_PATH)
    Call<ShopItem> addItem(@Body AddRequestBody body);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @PUT(Paths.ITEM_PATH)
    Call<ShopItem> updateItem(@Path("id") long id, @Body AddRequestBody body);

    @DELETE(Paths.ITEM_PATH)
    Call<Void> deleteItem(@Path("id") long id);
}
