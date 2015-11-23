package com.luckcheese.randomchooser;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {

    private String text;
    private long createdAt;

    public Item(String text) {
        this.text = text;
        this.createdAt = System.currentTimeMillis();
    }

    public String toListPrintable() {
        return text;
    }

    // ----- Related classes --------------------------------------------------

    public static class ItemsCollection extends ArrayList<Item> {

        private static String PREFS_KEY = "com.luckcheese.randomChooser.itemsCollection";
        private static Gson gson = new Gson();

        private static SharedPreferences getSharedPreferences(Context context) {
            return context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        }

        public static ItemsCollection getSaved(Context context) {
            String itemsJson = getSharedPreferences(context).getString("items", "[]");
            return gson.fromJson(itemsJson, ItemsCollection.class);
        }

        public static void save(Context context, ItemsCollection items) {
            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putString("items", gson.toJson(items));
            editor.apply();
        }

        public void add(String text) throws NullPointerException, CloneNotSupportedException {
            if (text == null || text.length() == 0) {
                throw new NullPointerException("Text can't be null");
            }
            for (Item i : this) {
                if (i.text.equals(text)) {
                    throw new CloneNotSupportedException("Item already exist");
                }
            }

            add(new Item(text));
        }
    }
}
