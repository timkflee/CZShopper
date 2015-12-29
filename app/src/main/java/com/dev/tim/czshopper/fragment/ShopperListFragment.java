package com.dev.tim.czshopper.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.tim.czshopper.AppManager;
import com.dev.tim.czshopper.R;
import com.dev.tim.czshopper.adapter.ShopAdapter;
import com.dev.tim.czshopper.view.HorizontalDividerItemDecoration;
import com.dev.tim.shopper_data.event.RequestEvent;

import java.util.ArrayList;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Tim on 12/27/2015.
 */
public class ShopperListFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ShopAdapter mAdapter;
    private OnAddClickListener mListener;
    private RefreshListener mRefreshListener;


    public static Fragment newInstance() {
        Fragment fragment = new ShopperListFragment();

        return fragment;
    }

    public ShopperListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnAddClickListener)
            mListener = (OnAddClickListener)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_shopperlist, container, false);

        mAdapter = new ShopAdapter();
        if (savedInstanceState != null) {
            mAdapter.setItems(savedInstanceState.getParcelableArrayList("itemList"));
        }

        mRecyclerView = (RecyclerView)view.findViewById(R.id.shopper_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setAdapter(mAdapter);
        Drawable divider = getResources().getDrawable(R.drawable.item_divider);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeLayout);
        mRefreshListener = new RefreshListener();

        FloatingActionButton button = (FloatingActionButton)view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAddClick();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(getString(R.string.app_name));
        mModel.getAllItems();
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSwipeRefreshLayout.setOnRefreshListener(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("itemList", (ArrayList<? extends Parcelable>) mAdapter.getItems());
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventSuccess(RequestEvent event) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setItems(event.getItems());
    }

    public interface OnAddClickListener {
        void onAddClick();
    }

    public static class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            AppManager.getShopperModel().getServerItems();
        }
    }
}
