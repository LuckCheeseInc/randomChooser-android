package com.luckcheese.randomchooser;

import android.content.SharedPreferences;
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
            currentFrag = ItemsListFragment.newFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, currentFrag)
                    .commit();
        }
    }
}
