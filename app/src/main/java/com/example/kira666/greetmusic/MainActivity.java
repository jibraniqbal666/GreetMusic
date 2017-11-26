package com.example.kira666.greetmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String ARGS_KEY = "getArgs";
//    public static final String ARGS_ALBUM_KEY = "AlbumTab";
//    public static final String ARGS_ARTIST_KEY = "ArtistTab";
//    public static final String ARGS_PLAYLIST_KEY = "PlaylistTab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

            Bundle argsSongs = new Bundle();
            argsSongs.putString(ARGS_KEY,getResources().getString(R.string.songs_tab));

            Bundle argsAlbum = new Bundle();
            argsAlbum.putString(ARGS_KEY,getResources().getString(R.string.album_tab));

            Bundle argsArtist = new Bundle();
            argsArtist.putString(ARGS_KEY,getResources().getString(R.string.artist_tab));

            Bundle argsPlaylist = new Bundle();
            argsPlaylist.putString(ARGS_KEY,getResources().getString(R.string.playlist_tab));

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
