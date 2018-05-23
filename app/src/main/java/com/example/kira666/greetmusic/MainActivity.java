package com.example.kira666.greetmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String ARGS_KEY = "getArgs";
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 100;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
//    public static final String ARGS_ALBUM_KEY = "AlbumTab";
//    public static final String ARGS_ARTIST_KEY = "ArtistTab";
//    public static final String ARGS_PLAYLIST_KEY = "PlaylistTab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        requestPermission();

    }

    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    setupViewPager(viewPager);
                    tabLayout.setupWithViewPager(viewPager);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle argsSongs = new Bundle();
        argsSongs.putString(ARGS_KEY, getResources().getString(R.string.songs_tab));

        Bundle argsAlbum = new Bundle();
        argsAlbum.putString(ARGS_KEY, getResources().getString(R.string.album_tab));

        Bundle argsArtist = new Bundle();
        argsArtist.putString(ARGS_KEY, getResources().getString(R.string.artist_tab));

        Bundle argsPlaylist = new Bundle();
        argsPlaylist.putString(ARGS_KEY, getResources().getString(R.string.playlist_tab));

        //SongsFragment
        TabFragment songsFragment = new TabFragment();
        songsFragment.setArguments(argsSongs);

        TabFragment artistFragment = new TabFragment();
        artistFragment.setArguments(argsArtist);

        TabFragment albumFragment = new TabFragment();
        albumFragment.setArguments(argsAlbum);

        TabFragment playlistFragment = new TabFragment();
        playlistFragment.setArguments(argsPlaylist);

        adapter.addFragment(songsFragment, getResources().getString(R.string.songs_tab));
        adapter.addFragment(albumFragment, getResources().getString(R.string.album_tab));
        adapter.addFragment(artistFragment, getResources().getString(R.string.artist_tab));
        adapter.addFragment(playlistFragment, getResources().getString(R.string.playlist_tab));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
