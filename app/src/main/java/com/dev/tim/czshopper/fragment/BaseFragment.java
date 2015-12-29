package com.dev.tim.czshopper.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import com.dev.tim.czshopper.AppManager;
import com.dev.tim.shopper_data.ShopperModel;

import de.greenrobot.event.EventBus;

/**
 * Created by Tim on 12/28/2015.
 */
public class BaseFragment extends Fragment {

    protected ShopperModel mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModel = AppManager.getShopperModel();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void goBack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }
}
