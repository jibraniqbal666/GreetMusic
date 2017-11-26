package com.example.kira666.greetmusic;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kira666 on 7/16/2017.
 */

public class CustomAdapterSongs extends BaseAdapter {
    private ArrayList<MusicModel> listData;
    private LayoutInflater layoutInflater;
    public CustomAdapterSongs(Context context, ArrayList<MusicModel> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_view_songs, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.artist = (TextView) convertView.findViewById(R.id.artist);
            holder.albumArt = (ImageView) convertView.findViewById(R.id.album_art);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(listData.get(position).getTitle());
        holder.artist.setText(listData.get(position).getArtist());
        holder.albumArt.setImageResource(R.drawable.music_art);
        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView artist;
        ImageView albumArt;
    }
}

