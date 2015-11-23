package com.luckcheese.randomchooser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class ItemsListFragment extends Fragment implements ItemsListAdapter.NewItemListener {

    private ItemsListAdapter itemsListAdapter;
    private Item.ItemsCollection items;

    public static ItemsListFragment newFragment() {
        ItemsListFragment frag = new ItemsListFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_items_list, container, false);
        populateView(fragView);
        return fragView;
    }

    private void populateView(View fragView) {
        ListView listView = (ListView) fragView.findViewById(R.id.listView);
        itemsListAdapter = new ItemsListAdapter(getActivity(), getItems(), this);
        listView.setAdapter(itemsListAdapter);
    }

    public Item.ItemsCollection getItems() {
        if (items == null) {
            items = Item.ItemsCollection.getSaved(getActivity());
        }
        return items;
    }

    // ----- ItemsListAdapter.NewItemListener ---------------------------------

    @Override
    public void onNewItemRequestedToBeCreated(View newItemView) {
        Item.ItemsCollection items = getItems();

        EditText editText = (EditText) newItemView.findViewById(R.id.newItemText);
        try {
            items.add(editText.getText().toString());
            Item.ItemsCollection.save(getActivity(), items);

            itemsListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            editText.setError(e.getMessage());
        }
    }
}
