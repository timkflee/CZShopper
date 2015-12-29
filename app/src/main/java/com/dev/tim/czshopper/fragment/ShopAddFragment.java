package com.dev.tim.czshopper.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dev.tim.czshopper.R;
import com.dev.tim.czshopper.util.DialogFactory;
import com.dev.tim.czshopper.util.ToastFactory;
import com.dev.tim.shopper_data.event.FailureEvent;
import com.dev.tim.shopper_data.event.ItemCreatedEvent;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Tim on 12/27/2015.
 */
public class ShopAddFragment extends BaseFragment {

    private EditText nameEditText;
    private EditText categoryEditText;
    private ProgressDialog progressDialog;

    public static Fragment newInstance() {
        Fragment fragment = new ShopAddFragment();
        return fragment;
    }

    public ShopAddFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_shopadd, container, false);
        nameEditText = (EditText)v.findViewById(R.id.name_editview);
        categoryEditText = (EditText)v.findViewById(R.id.category_editview);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.add_title));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addAction:
                // Verify fields are filled
                hideKeyboard();
                if (nameEditText.getText().toString().isEmpty() ||
                        categoryEditText.getText().toString().isEmpty()) {
                    AlertDialog dialog = DialogFactory.newDialog(getActivity(), getString(R.string.missing_field), null);
                    dialog.show();
                    return true;
                }

                if (progressDialog != null)
                    return true;

                progressDialog = DialogFactory.newProgressDialog(getActivity(), getString(R.string.adding), false);
                progressDialog.show();

                mModel.addItem(nameEditText.getText().toString(), categoryEditText.getText().toString());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onItemCreated(ItemCreatedEvent event) {
        progressDialog.dismiss();
        ToastFactory.newToast(getContext(), getString(R.string.item_created));
        goBack();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onItemCreatedFailure(FailureEvent event) {
        if (progressDialog != null)
            progressDialog.dismiss();
        AlertDialog dialog = DialogFactory.newDialog(getActivity(), event.getMessage(), null);
        dialog.show();
    }
}
