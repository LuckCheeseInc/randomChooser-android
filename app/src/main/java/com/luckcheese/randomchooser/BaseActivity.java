package com.luckcheese.randomchooser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup contentContainer = (ViewGroup) findViewById(R.id.content);
        getLayoutInflater().inflate(layoutResID, contentContainer);
    }

    @Override
    public void setContentView(View view) {
        ViewGroup contentContainer = (ViewGroup) findViewById(R.id.content);
        contentContainer.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        ViewGroup contentContainer = (ViewGroup) findViewById(R.id.content);
        contentContainer.addView(view, params);
    }
}
