package com.example.myvideoapp;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class View_Holder extends RecyclerView.ViewHolder {
    Button button;
    public View_Holder(@NonNull View itemView) {
        super(itemView);
        button=itemView.findViewById(R.id.button_recycler);
    }
}
