package com.luckcheese.randomchooser;

import android.os.Bundle;

public class RandomChooserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configFragment();
    }

    private void configFragment() {
        ItemsListFragment currentFrag = (ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (currentFrag == null) {
            currentFrag = new ItemsListFragment();
            currentFrag.setItems(getItems());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, currentFrag)
                    .commit();
        }
    }

    private Item.ItemsCollection getItems() {
        Item.ItemsCollection items = new Item.ItemsCollection();

        for (int i = 0; i < 20; i++) {
            items.add(new Item("Item " + i));
        }

        return items;
    }
}
