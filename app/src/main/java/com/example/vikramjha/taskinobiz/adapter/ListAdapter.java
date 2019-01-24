package com.example.vikramjha.taskinobiz.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikramjha.taskinobiz.R;


/**
 * Created by iDreamz on 3/22/2017.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    // create constructor to innitilize context and data sent from MainActivity
    public ListAdapter(Context context) {
        this.context = context;


    }


    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        MyHolder holder = new MyHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

    }


    @Override
    public int getItemCount() {
        return 10;

    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textView ;
        ImageView image;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);




        }

    }
}
