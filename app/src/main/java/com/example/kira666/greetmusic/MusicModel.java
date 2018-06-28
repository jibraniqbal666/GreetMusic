package com.example.kira666.greetmusic;

/**
 * Created by Kira666 on 7/14/2017.
 */

public class MusicModel {
    private long id;
    private String title;
    private String artist;
    private String album;
    private String path;
    private String albumId;
    private String artistId;
    private String albumArt;


    public MusicModel(long id, String title, String artist, String album, String path, String albumId, String artistId, String albumArt) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.path = path;
        this.albumId = albumId;
        this.artistId = artistId;
        this.albumArt = albumArt;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getPath() {
        return path;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getAlbumArt() {
        return albumArt;
    }
}
