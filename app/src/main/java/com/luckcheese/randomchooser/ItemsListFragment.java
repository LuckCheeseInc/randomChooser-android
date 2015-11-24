package com.luckcheese.randomchooser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.item_options, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;

        switch (item.getItemId()) {

            case R.id.edit:
                itemsListAdapter.setEditingItemPos(position);
                return true;

            case R.id.delete:
                deleteItem(position);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public Item.ItemsCollection getItems() {
        if (items == null) {
            items = Item.ItemsCollection.getSaved(getActivity());
        }
        return items;
    }

    private void saveItemsAndUpdateView(Item.ItemsCollection items) {
        Item.ItemsCollection.save(getActivity(), items);
        itemsListAdapter.notifyDataSetChanged();
    }

    private void deleteItem(int position) {
        Item.ItemsCollection items = getItems();
        items.remove(position);
        saveItemsAndUpdateView(items);
    }

    // ----- ItemsListAdapter.NewItemListener ---------------------------------

    @Override
    public boolean onNewItemRequestedToBeCreated(ItemsListAdapter.NewItemViewHolder newItemView, Item editingItem) {
        Item.ItemsCollection items = getItems();

        String newText = newItemView.editText.getText().toString();
        try {
            if (editingItem != null) {
                items.editItem(editingItem, newText);
                saveItemsAndUpdateView(items);
            }
            else {
                items.add(newText);
                saveItemsAndUpdateView(items);
            }
            return true;
        } catch (Exception e) {
            newItemView.editText.setError(e.getMessage());
            return false;
        }
    }
}
