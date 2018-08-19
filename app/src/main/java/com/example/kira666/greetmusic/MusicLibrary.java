package com.example.kira666.greetmusic;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kira666 on 7/14/2017.
 */

public class MusicLibrary {
    ContentResolver musicResolver;
    Context context;
    private Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static MusicLibrary instance;
    List<MusicModel> musicModels;
    List<String> songs;
    List<String> artists;
    List<String> albums;

    private MusicLibrary(Context context) {
        this.context = context;
        musicResolver = context.getContentResolver();
    }

    public static MusicLibrary getInstance(Context context) {
        if (instance == null) {
            return new MusicLibrary(context);
        }
        return instance;
    }

    public static Uri getAlbumArtPath(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    public List<MusicModel> getMusicLib() {
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, sortOrder);
        if (musicCursor != null) {
            if (musicModels == null) {
                musicModels = new ArrayList<>();
            }
            if (musicCursor.getCount() > musicModels.size()) {
                if (musicCursor.moveToFirst()) {
                    //get columns
                    int titleColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media.TITLE);
                    int idColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media._ID);
                    int artistColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media.ARTIST);
                    int albumColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media.ALBUM);
                    int idartistColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media.ARTIST_ID);
                    int idalbumColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media.ALBUM_ID);

                    int pathColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media.DATA);

                    //add songs to list
                    do {
                        long id = musicCursor.getLong(idColumn);
                        String title = musicCursor.getString(titleColumn);
                        String artist = musicCursor.getString(artistColumn);
                        String album = musicCursor.getString(albumColumn);
                        String path = musicCursor.getString(pathColumn);
                        long idartist = musicCursor.getLong(idartistColumn);
                        long idalbum = musicCursor.getLong(idalbumColumn);
                        Uri albumart = getAlbumArtPath(idalbum);


                        musicModels.add(new MusicModel(id, title, artist, album, path, String.valueOf(idalbum), String.valueOf(idartist), albumart));
                    }
                    while (musicCursor.moveToNext());
                }
            }
            musicCursor.close();
        }
        return musicModels;
    }

    public void getSongs() {
        if (musicModels.size() > 0) {
            songs = new ArrayList<>();
            for (MusicModel musicModel : musicModels) {
                songs.add(musicModel.getTitle());
            }
        }
    }

    public List<String> getArtist() {
        String[] mProjection =
                {
                        MediaStore.Audio.Media.ARTIST
                };
        String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC";
        Cursor musicCursor = musicResolver.query(musicUri, mProjection, null, null, sortOrder);
        if (musicCursor != null) {
            if (artists == null) {
                artists = new ArrayList<>();
            }
            if (musicCursor.getCount() > artists.size()) {
                if (musicCursor.moveToFirst()) {
                    //get columns
                    int artistColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media.ARTIST);
                    //add songs to list
                    do {
                        String artist = musicCursor.getString(artistColumn);
                        artists.add(artist);
                    }
                    while (musicCursor.moveToNext());
                }
            }
            musicCursor.close();
        }
        return artists;
    }

    public List<String> getAlbum() {
        String[] mProjection =
                {
                        MediaStore.Audio.Media.ALBUM
                };
        String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";
        Cursor musicCursor = musicResolver.query(musicUri, mProjection, null, null, sortOrder);
        if (musicCursor != null) {
            if (albums == null) {
                albums = new ArrayList<>();
            }
            if (musicCursor.getCount() > albums.size()) {
                if (musicCursor.moveToFirst()) {
                    //get columns
                    int albumColumn = musicCursor.getColumnIndex
                            (MediaStore.Audio.Media.ALBUM);
                    //add songs to list
                    do {
                        String album = musicCursor.getString(albumColumn);
                        albums.add(album);
                    }
                    while (musicCursor.moveToNext());
                }
            }
            musicCursor.close();
        }
        return albums;
    }
}
