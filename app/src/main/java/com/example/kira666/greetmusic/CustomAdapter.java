package com.example.kira666.greetmusic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    List<String> arraylist;
    private LayoutInflater inflater;
    private Context context;

    public CustomAdapter(Context context, List<String> arraylist) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.arraylist = arraylist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(arraylist.get(position));
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }
}