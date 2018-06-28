package com.example.kira666.greetmusic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Kira666 on 7/13/2017.
 */

public class TabFragment extends Fragment {
    public final static String EXTRAS_MUSIC_PATH = "getMusicPath";
    public final static String EXTRAS_MUSIC_ID = "getMusicId";
    public final static String EXTRAS_MUSIC_TITLE = "getMusicTitle";
    public final static String EXTRAS_MUSIC_ARTIST = "getMusicArtist";
    public final static String EXTRAS_MUSIC_ALBUM = "getMusicAlbum";
    public final static String EXTRAS_MUSIC_ARTIST_ID = "getMusicArtistId";
    public final static String EXTRAS_MUSIC_ALBUM_ID = "getMusicAlbumId";

    public final int REQUEST_EXTERNAL_STORAGE = 1;
    public final int REQUEST_WAKE_LOCK = 2;
    private RecyclerView listView;
    private ArrayList<MusicModel> music;
    private ArrayList<String> albums;
    private ArrayList<String> artists;

    private MusicLibrary musicLibrary;
    private boolean storagePermissionStatus = false;
    private boolean wakelockPermissionStatus = false;

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WAKE_LOCK)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WAKE_LOCK},
                        REQUEST_WAKE_LOCK);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            storagePermissionStatus = true;
            wakelockPermissionStatus = true;
        }

        View rootView = inflater.inflate(R.layout.fragment_one, container, false);

        listView = rootView.findViewById(R.id.recycler);
        listView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        if (storagePermissionStatus) {
            String getArgs;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
            musicLibrary = new MusicLibrary(getContext());

            if ((getArgs = getArguments().getString(MainActivity.ARGS_KEY)) != null) {
                switch (getArgs) {
                    case "Songs":
                        new GetMusic().execute();
                        return rootView;
                    case "Albums":
                        new GetAlbum().execute();
                        return rootView;
                    case "Artists":
                        new GetArtist().execute();
                        return rootView;
                    case "Playlists":
                        break;
                }
            }


        }
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
            }
            break;
            case REQUEST_WAKE_LOCK: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
            }
            break;
        }


        // other 'case' lines to check for other
        // permissions this app might request
    }

    class GetMusic extends AsyncTask<Void, Void, ArrayList<MusicModel>> {
        @Override
        protected ArrayList<MusicModel> doInBackground(Void... voids) {
            if (music != null) {
                return null;
            }
            return musicLibrary.getMusicLib();
        }

        @Override
        protected void onPostExecute(final ArrayList<MusicModel> musicModels) {
            if (music == null) {
                music = musicModels;
            }
            listView.setAdapter(new CustomAdapterSongs(getActivity().getApplicationContext(), music, new CustomAdapterSongs.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {

                    MusicModel musicModel = music.get(position);

                    Intent intent = new Intent(getContext(), PlayMusic.class);
                    intent.putExtra(EXTRAS_MUSIC_ID, musicModel.getId());
                    intent.putExtra(EXTRAS_MUSIC_PATH, musicModel.getPath());
                    intent.putExtra(EXTRAS_MUSIC_TITLE, musicModel.getTitle());
                    intent.putExtra(EXTRAS_MUSIC_ALBUM, musicModel.getAlbum());
                    intent.putExtra(EXTRAS_MUSIC_ARTIST, musicModel.getArtist());
                    intent.putExtra(EXTRAS_MUSIC_ALBUM_ID, musicModel.getAlbumId());
                    intent.putExtra(EXTRAS_MUSIC_ARTIST_ID, musicModel.getArtistId());

                    startActivity(intent);
                }
            }));
        }
    }

    class GetAlbum extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            if (albums != null) {
                return null;
            }
            return musicLibrary.getAlbum();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            if (albums == null) {
                albums = strings;
            }
            listView.setAdapter(new CustomAdapter(getContext(), albums));
        }
    }

    class GetArtist extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            if (artists != null) {
                return null;
            }
            return musicLibrary.getArtist();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            if (artists == null) {
                artists = strings;
            }
            listView.setAdapter(new CustomAdapter(getContext(), artists));
        }
    }

}
