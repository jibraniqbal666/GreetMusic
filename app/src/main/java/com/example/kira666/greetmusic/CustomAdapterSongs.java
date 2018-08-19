package com.example.kira666.greetmusic;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Kira666 on 7/16/2017.
 */

public class CustomAdapterSongs extends RecyclerView.Adapter<CustomAdapterSongs.ViewHolder> {
    private List<MusicModel> listData;
    public OnItemClickListener listener;
    private Context context;

    public CustomAdapterSongs(Context context, List<MusicModel> listData, OnItemClickListener onItemClickListener) {
        this.listData = listData;
        this.context = context;
        listener = onItemClickListener;
    }

    @Override
    public long getItemId(int position) {
        return listData.get(position).getId();
    }

    public CustomAdapterSongs.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_songs, parent, false);

        final ViewHolder viewHolder = new CustomAdapterSongs.ViewHolder(v, context);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.title.setText(listData.get(position).getTitle());
        holder.artist.setText(listData.get(position).getArtist());


        Uri albumArt = listData.get(position).getAlbumArt();

        if (albumArt != null) {
            Glide.with(context)
                    .load(albumArt)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.no_album_1)
                            .error(R.drawable.no_album_1)
                    )
                    .into(holder.albumArt);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView artist;
        ImageView albumArt;
        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            artist = itemView.findViewById(R.id.artist);
            albumArt = itemView.findViewById(R.id.album_art);
            this.context = context;


        }
    }
}

