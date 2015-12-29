package com.dev.tim.czshopper.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dev.tim.czshopper.R;
import com.dev.tim.czshopper.util.DialogFactory;
import com.dev.tim.czshopper.util.ToastFactory;
import com.dev.tim.shopper_data.event.DeleteSuccessEvent;
import com.dev.tim.shopper_data.event.EditSuccessEvent;
import com.dev.tim.shopper_data.event.FailureEvent;
import com.dev.tim.shopper_rest.object.ShopItem;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Tim on 12/27/2015.
 */
public class ShopEditFragment extends BaseFragment implements View.OnClickListener {

    private EditText categoryEditView;
    private EditText nameEditView;
    private ShopItem item;
    private ProgressDialog progressDialog;

    public static Fragment newInstance(ShopItem item) {
        Fragment fragment = new ShopEditFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ShopEditFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            item = savedInstanceState.getParcelable("item");
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            item = bundle.getParcelable("item");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_shopedit, container, false);
        nameEditView = (EditText)v.findViewById(R.id.name_editview);
        categoryEditView = (EditText)v.findViewById(R.id.category_editview);

        if (item != null) {
            nameEditView.setText(item.getName());
            categoryEditView.setText(item.getCategory());
        }
        Button editButton = (Button)v.findViewById(R.id.edit_button);
        Button cancelButton = (Button)v.findViewById(R.id.cancel_button);
        editButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.edit_title));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("item", item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.removeAction:
                // Verify fields are filled
                AlertDialog dialog = DialogFactory.newDialog(getActivity(), getString(R.string.delete_item),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ShopEditFragment.this.showProgressDialog(getString(R.string.deleting));
                                mModel.deleteItem(ShopEditFragment.this.item.getId());
                            }
                        }, null);
                dialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.edit_button:
                editAction();
                break;
            case R.id.cancel_button:
                hideKeyboard();
                goBack();
                break;
        }
    }

    public void editAction() {
        hideKeyboard();
        if (nameEditView.getText().toString().isEmpty() ||
                categoryEditView.getText().toString().isEmpty()) {
            AlertDialog dialog = DialogFactory.newDialog(getActivity(), getString(R.string.missing_field), null);
            dialog.show();
            return;
        }

        // Some other dialog still in progress
        if (progressDialog != null)
            return;

        showProgressDialog(getString(R.string.updating));
        mModel.updateItem(item.getId(), nameEditView.getText().toString(), categoryEditView.getText().toString());
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEditSuccess(EditSuccessEvent event) {
        dismissProgressDialog();
        ToastFactory.newToast(getContext(), getString(R.string.item_updated));
        goBack();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onDeleteSuccess(DeleteSuccessEvent event) {
        dismissProgressDialog();
        ToastFactory.newToast(getContext(), getString(R.string.item_deleted));
        goBack();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onFailure(FailureEvent event) {
        dismissProgressDialog();
        AlertDialog dialog = DialogFactory.newDialog(getActivity(), event.getMessage(), null);
        dialog.show();
    }

    public void showProgressDialog(String message) {
        progressDialog = DialogFactory.newProgressDialog(getActivity(), message, false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
