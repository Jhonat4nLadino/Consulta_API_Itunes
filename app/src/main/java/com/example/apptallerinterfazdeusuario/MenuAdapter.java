package com.example.apptallerinterfazdeusuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MenuAdapter extends ArrayAdapter {

    String[] data;

    public MenuAdapter(Context context, String[] data) {
        super(context, R.layout.adapter_menu, data);
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItemMenu;

        listItemMenu = null == convertView ? layoutInflater.inflate(
                R.layout.adapter_menu,
                parent,
                false) : convertView;

        TextView textMenu = listItemMenu.findViewById(R.id.textAdapterMenu);
        ImageView imageMenu = listItemMenu.findViewById(R.id.imageAdapterMenu);

        textMenu.setText(data[position]);
        if (position == 0) {
            imageMenu.setImageResource(R.drawable.ic_baseline_menu_24);
        } else {
            imageMenu.setImageResource(R.drawable.ic_baseline_lock_24);
        }

        return listItemMenu;
    }
}
