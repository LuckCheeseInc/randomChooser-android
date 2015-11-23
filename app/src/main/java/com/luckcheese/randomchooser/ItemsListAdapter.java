package com.luckcheese.randomchooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemsListAdapter extends ArrayAdapter<Item> implements View.OnClickListener {

    private LayoutInflater inflater;
    private NewItemListener listener;

    public ItemsListAdapter(Context context, List<Item> objects, NewItemListener listener) {
        super(context, R.layout.item_random_chooser, objects);
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public Item getItem(int position) {
        if (position == getCount() - 1) {
            return null;
        }
        else {
            return super.getItem(position);
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case 1:
                return getNewItemView(convertView, parent);

            default:
                return getItemView(position, convertView, parent);
        }
    }

    private View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_random_chooser, parent, false);
        }

        Item item = getItem(position);
        ((TextView) convertView.findViewById(R.id.text)).setText(item.toListPrintable());

        return convertView;
    }

    private View getNewItemView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.new_item_random_chooser, parent, false);
            convertView.findViewById(R.id.submit).setOnClickListener(this);
        }
        return convertView;
    }

    // ----- View.OnClickListener ---------------------------------------------

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                listener.onNewItemRequestedToBeCreated((View) view.getParent());
                break;
        }
    }

    // ----- Related classes --------------------------------------------------

    public interface NewItemListener {
        void onNewItemRequestedToBeCreated(View newItemView);
    }
}
