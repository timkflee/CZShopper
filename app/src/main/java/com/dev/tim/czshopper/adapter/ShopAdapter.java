package com.dev.tim.czshopper.adapter;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.tim.czshopper.R;
import com.dev.tim.shopper_data.event.ItemEvent;
import com.dev.tim.shopper_rest.object.ShopItem;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Tim on 12/27/2015.
 */
public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<? extends Parcelable> items = Collections.emptyList();
    public final static int HEADER_TYPE = 1;
    public final static int ROW_TYPE = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int itemType) {

        View v = null;
        if (itemType == HEADER_TYPE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_header, parent, false);
            return new HeaderHolder(v);
        }

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new RowHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RowHolder)
            ((RowHolder)viewHolder).bindItem((ShopItem)items.get(position-1));
    }

    @Override
    public int getItemCount() {
        if (items.isEmpty())
            return 1;
        return items.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0)
            return ROW_TYPE;
        return HEADER_TYPE;
    }

    public void setItems(List<? extends Parcelable> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<? extends Parcelable> getItems() {
        return items;
    }

    public static class RowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView categoryTextView;
        private TextView nameTextView;
        private ShopItem item;

        public RowHolder(View itemView) {
            super(itemView);
            categoryTextView = (TextView) itemView.findViewById(R.id.category_text);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text);
            itemView.setOnClickListener(this);
        }

        public void bindItem(ShopItem item) {
            categoryTextView.setText(item.getCategory());
            nameTextView.setText(item.getName());
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            if (item != null)
                EventBus.getDefault().post(new ItemEvent(item));
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }
}
