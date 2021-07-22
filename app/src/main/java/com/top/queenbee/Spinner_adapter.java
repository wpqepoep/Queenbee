package com.top.queenbee;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Spinner_adapter extends ArrayAdapter<Information> {
    Context context;
    ArrayList<Information> ItemList;

    public Spinner_adapter(@NonNull Context context,  ArrayList<Information> ItemList) {
        super(context,0, ItemList);
        this.context = context;
        this.ItemList = ItemList;
    }
}
