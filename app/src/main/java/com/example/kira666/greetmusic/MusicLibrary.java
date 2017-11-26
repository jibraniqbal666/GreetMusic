package com.example.kira666.greetmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Kira666 on 7/14/2017.
 */

public class MusicLibrary {
    private Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    ContentResolver musicResolver;
    Context context;
    public MusicLibrary(Context context) {
        this.context = context;
        musicResolver = context.getContentResolver();
    }
    public ArrayList<MusicModel> getMusicLib(){
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, sortOrder);
        ArrayList<MusicModel> musicModels = new ArrayList<>();
        if(musicCursor!=null && musicCursor.moveToFirst()){
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
                String idartist = musicCursor.getString(idartistColumn);
                String idalbum = musicCursor.getString(idalbumColumn);

                musicModels.add(new MusicModel(id,title,artist,album,path,idalbum,idartist));
            }
            while (musicCursor.moveToNext());
        }
        return musicModels;
    }
    public ArrayList<String> getSongs(){
        ArrayList<MusicModel> musicModels = getMusicLib();
        if(musicModels.size() > 0) {
            ArrayList<String> songsArray = new ArrayList<>();
            for (MusicModel musicModel : musicModels) {
                songsArray.add(musicModel.getTitle());
            }
            return songsArray;
        }
        return null;
    }
    public ArrayList<String> getArtist(){
        String[] mProjection =
                {
                        MediaStore.Audio.Media.ARTIST
                };
        String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC";
        Cursor musicCursor = musicResolver.query(musicUri,mProjection, null, null, sortOrder);
        ArrayList<String> artistArray = new ArrayList<>();
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                String artist = musicCursor.getString(artistColumn);
                artistArray.add(artist);
            }
            while (musicCursor.moveToNext());
        }
        return artistArray;
    }
    public ArrayList<String> getAlbum(){
        String[] mProjection =
                {
                        MediaStore.Audio.Media.ALBUM
                };
        String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";
        Cursor musicCursor = musicResolver.query(musicUri,mProjection, null, null, sortOrder);
        ArrayList<String> albumArray = new ArrayList<>();
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            //add songs to list
            do {
                String album = musicCursor.getString(albumColumn);
                albumArray.add(album);
            }
            while (musicCursor.moveToNext());
        }
        return albumArray;
    }
    public String getAlbumArtPath(String albumId){
        Cursor cursor = musicResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID+ "=?",
                new String[] {String.valueOf(albumId)},
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        }
        return null;
    }
//    public ArrayList<String> getNonDuplicates(ArrayList<String> arrayList){
//        if(arrayList.size() > 1) {
//            Set<String> nonDuplicateString = new HashSet<>();
//            for (String value : arrayList) {
//                if(!nonDuplicateString.add(value)){
//                    Log.d("MusicLibDups",value);
//                }
//            }
//
//            ArrayList<String> nonDuplicateArray = new ArrayList<>();
//
//            for (String value : nonDuplicateString) {
//                nonDuplicateArray.add(value);
//            }
//            return nonDuplicateArray;
//        }
//
//        return arrayList;
//    }
}
