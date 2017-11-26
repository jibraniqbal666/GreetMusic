package com.example.kira666.greetmusic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kira666 on 7/13/2017.
 */

public class TabFragment extends Fragment {
    private ListView listView;
    private boolean storagePermissionStatus = false;
    private boolean wakelockPermissionStatus = false;
    public final int REQUEST_EXTERNAL_STORAGE = 1;
    public final int REQUEST_WAKE_LOCK = 2;
    public final static String EXTRAS_MUSIC_PATH = "getMusicPath";
    public final static String EXTRAS_MUSIC_ID = "getMusicId";
    public final static String EXTRAS_MUSIC_TITLE = "getMusicTitle";
    public final static String EXTRAS_MUSIC_ARTIST = "getMusicArtist";
    public final static String EXTRAS_MUSIC_ALBUM = "getMusicAlbum";
    public final static String EXTRAS_MUSIC_ARTIST_ID = "getMusicArtistId";
    public final static String EXTRAS_MUSIC_ALBUM_ID = "getMusicAlbumId";

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);


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
        }
        else {
            storagePermissionStatus = true;
            wakelockPermissionStatus = true;
        }

        View rootView = inflater.inflate(R.layout.fragment_one,container,false);

        listView = (ListView) rootView.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicModel musicModel = (MusicModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext().getApplicationContext(),PlayMusic.class);

                intent.putExtra(EXTRAS_MUSIC_ID,musicModel.getId());
                intent.putExtra(EXTRAS_MUSIC_PATH,musicModel.getPath());
                intent.putExtra(EXTRAS_MUSIC_TITLE,musicModel.getTitle());
                intent.putExtra(EXTRAS_MUSIC_ALBUM,musicModel.getAlbum());
                intent.putExtra(EXTRAS_MUSIC_ARTIST,musicModel.getArtist());
                intent.putExtra(EXTRAS_MUSIC_ALBUM_ID,musicModel.getAlbumId());
                intent.putExtra(EXTRAS_MUSIC_ARTIST_ID,musicModel.getArtistId());

                startActivity(intent);
            }
        });

        if(storagePermissionStatus){
            String getArgs;
            if((getArgs = getArguments().getString(MainActivity.ARGS_KEY))!=null) {
                switch (getArgs) {
                    case "Songs":
                        listView.setAdapter(new CustomAdapterSongs(getActivity().getApplicationContext(),new MusicLibrary(getActivity().getApplicationContext()).getMusicLib()));
                        return rootView;
                    case "Albums":
                        listView.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,new MusicLibrary(getActivity().getApplicationContext()).getAlbum()));
                        return rootView;
                    case "Artists":
                        listView.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,new MusicLibrary(getActivity().getApplicationContext()).getArtist()));
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

}
