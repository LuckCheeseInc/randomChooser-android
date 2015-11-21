package com.luckcheese.randomchooser;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {

    private String text;

    public Item(String text) {
        this.text = text;
    }

    public String toListPrintable() {
        return "Felicidade";
    }

    // ----- Related classes --------------------------------------------------

    public static class ItemsCollection extends ArrayList<Item> {

    }
}
