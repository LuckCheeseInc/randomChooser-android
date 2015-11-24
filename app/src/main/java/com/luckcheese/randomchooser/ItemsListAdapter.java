package com.luckcheese.randomchooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ItemsListAdapter extends ArrayAdapter<Item> implements View.OnClickListener {

    private LayoutInflater inflater;
    private NewItemListener listener;

    private int editingItemPos = -1;

    public ItemsListAdapter(Context context, List<Item> objects, NewItemListener listener) {
        super(context, R.layout.item_random_chooser, objects);
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    public void setEditingItemPos(int position) {
        this.editingItemPos = position;
        this.notifyDataSetChanged();
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
        if (position == getCount() - 1 || position == editingItemPos) {
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
                Item editingItem = null;
                if (position == editingItemPos) {
                    editingItem = getItem(position);
                }
                return getNewItemView(convertView, parent, editingItem);

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

    private View getNewItemView(View convertView, ViewGroup parent, Item editingItem) {
        NewItemViewHolder viewHolder;
        if (convertView == null || editingItem == null) {
            convertView = inflater.inflate(R.layout.new_item_random_chooser, parent, false);
            viewHolder = new NewItemViewHolder(convertView);
            viewHolder.setButtonsOnClickListener(this);
        }
        else {
            viewHolder = (NewItemViewHolder) convertView.getTag();
        }

        viewHolder.setEditingItem(editingItem);

        return convertView;
    }

    // ----- View.OnClickListener ---------------------------------------------

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cancel:
                editingItemPos = -1;
                notifyDataSetChanged();
                break;

            case R.id.submit:
                NewItemViewHolder parentView = (NewItemViewHolder) ((View) view.getParent()).getTag();
                boolean success = listener.onNewItemRequestedToBeCreated(parentView, (Item) view.getTag());

                if (success) {
                    parentView.editText.setText("");
                    editingItemPos = -1;
                }
                break;
        }
    }

    // ----- Related classes --------------------------------------------------

    public interface NewItemListener {
        boolean onNewItemRequestedToBeCreated(NewItemViewHolder newItemView, Item editingItem);
    }

    public static class NewItemViewHolder {
        public Button submitBtn;
        public Button cancelBtn;
        public EditText editText;

        public NewItemViewHolder(View newItemView) {
            submitBtn = (Button) newItemView.findViewById(R.id.submit);
            cancelBtn = (Button) newItemView.findViewById(R.id.cancel);
            editText = (EditText) newItemView.findViewById(R.id.newItemText);

            newItemView.setTag(this);
        }

        public void setEditingItem(Item item) {
            submitBtn.setTag(item);

            if (item != null) {
                editText.setText(item.toListPrintable());
                cancelBtn.setVisibility(View.VISIBLE);
            }
            else {
                cancelBtn.setVisibility(View.GONE);
            }
        }

        public void setButtonsOnClickListener(View.OnClickListener listener) {
            submitBtn.setOnClickListener(listener);
            cancelBtn.setOnClickListener(listener);
        }
    }
}
