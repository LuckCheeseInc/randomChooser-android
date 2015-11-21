package com.luckcheese.randomchooser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ItemsListFragment extends Fragment {

    private static String ARG_ITEMS = "arg_items";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_items_list, container, false);
        populateView(fragView);
        return fragView;
    }

    private void populateView(View fragView) {
        ListView listView = (ListView) fragView.findViewById(R.id.listView);
        listView.setAdapter(new ItemsListAdapter(getActivity(), getItems()));
    }

    public Item.ItemsCollection getItems() {
        Bundle b = getArguments();
        return (Item.ItemsCollection) b.getSerializable(ARG_ITEMS);
    }

    public void setItems(Item.ItemsCollection items) {
        Bundle b = getArguments();
        if (b == null) {
            b = new Bundle();
        }
        b.putSerializable(ARG_ITEMS, items);
        setArguments(b);
    }
}
