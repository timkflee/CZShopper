package com.dev.tim.czshopper;

import android.app.Application;

import com.dev.tim.czshopper.constant.Host;
import com.dev.tim.shopper_data.DBOpenHelper;
import com.dev.tim.shopper_data.ShopperModel;
import com.dev.tim.shopper_rest.Connector;

import de.greenrobot.event.EventBus;

/**
 * Created by Tim on 12/27/2015.
 */
public class AppManager extends Application {

    private Connector mConnector;
    private static ShopperModel mShopperModel;

    @Override
    public void onCreate() {
        super.onCreate();

        mConnector = new Connector(this, Host.HOST, Host.AUTHCODE);
        DBOpenHelper.init(this);
        mShopperModel = new ShopperModel(this, mConnector.getShopperService(), EventBus.getDefault());
    }

    public static ShopperModel getShopperModel() {
        return mShopperModel;
    }
}
