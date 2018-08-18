package com.vtt.musiconline.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vtt.musiconline.adapter.AlbumAdapter;
import com.vtt.musiconline.adapter.CategoryAdapter;
import com.vtt.musiconline.adapter.PlaylistAdapter;
import com.vtt.musiconline.api.asynctask.GetAlbumTask;
import com.vtt.musiconline.api.asynctask.GetCategoryTask;
import com.vtt.musiconline.api.asynctask.GetPlayListTask;
import com.vtt.musiconline.model.Album;
import com.vtt.musiconline.model.Category;
import com.vtt.musiconline.model.Playlist;
import com.vtt.musiconline.R;
import com.vtt.musiconline.utils.CircleProgressBar;
import com.vtt.musiconline.view.ListActivity;
import com.vtt.musiconline.view.ListSongActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MainFragment extends Fragment {
    RelativeLayout title_playlist, title_album, title_category;
    PlaylistAdapter adapterPlaylist;
    AlbumAdapter adapterAlbum;
    CategoryAdapter adapterCate;
    RecyclerView recyclerViewPlayList, recyclerViewAlbum, recyclerViewCate;
    static List<Playlist> listPlaylists = new ArrayList<>();
    static List<Album> listAlbums = new ArrayList<>();
    static List<Category> listCategories = new ArrayList<>();
    static String sPlayList, sAlbum, sCate;

    public static MainFragment newInstance(String gPlaylist, String gAlbum, String gCate) {
        TypeToken<List<Playlist>> tokenPlaylist = new TypeToken<List<Playlist>>() {
        };
        listPlaylists = new Gson().fromJson(gPlaylist, tokenPlaylist.getType());
        sPlayList = gPlaylist;

        TypeToken<List<Album>> tokenAlbum = new TypeToken<List<Album>>() {
        };
        listAlbums = new Gson().fromJson(gAlbum, tokenAlbum.getType());
        sAlbum = gAlbum;

        TypeToken<List<Category>> tokenCate = new TypeToken<List<Category>>() {
        };
        listCategories = new Gson().fromJson(gCate, tokenCate.getType());
        sCate = gCate;
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        title_playlist = view.findViewById(R.id.title_playlist);
        title_album = view.findViewById(R.id.title_album);
        title_category = view.findViewById(R.id.title_category);

        recyclerViewPlayList = view.findViewById(R.id.rcv_playlist);
        GridLayoutManager managerPlaylist = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewPlayList.setLayoutManager(managerPlaylist);
        recyclerViewPlayList.setNestedScrollingEnabled(false);
        ArrayList<Playlist> listSortPlaylist = new ArrayList<>();
        for (int i = 0; i < listPlaylists.size(); i++) {
            if (i < 3) {
                Playlist playlist = listPlaylists.get(i);
                listSortPlaylist.add(playlist);
            }
        }
        adapterPlaylist = new PlaylistAdapter(getActivity(), listSortPlaylist, (playlist, position) -> {
            Intent intent = new Intent(getActivity(), ListSongActivity.class);
            intent.putExtra("id_playlist", playlist.getIdPlaylist());
            intent.putExtra("name", playlist.getNamePlaylist());
            startActivity(intent);
        });
        recyclerViewPlayList.setAdapter(adapterPlaylist);

        recyclerViewAlbum = view.findViewById(R.id.rcv_album);
        GridLayoutManager managerAlbum = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewAlbum.setLayoutManager(managerAlbum);
        recyclerViewAlbum.setNestedScrollingEnabled(false);
        ArrayList<Album> listSortAlbum = new ArrayList<>();
        for (int i = 0; i < listAlbums.size(); i++) {
            if (i < 3) {
                Album album = listAlbums.get(i);
                listSortAlbum.add(album);
            }

        }
        adapterAlbum = new AlbumAdapter(getActivity(), listSortAlbum, (album1, position) -> {
            Intent intent = new Intent(getActivity(), ListSongActivity.class);
            intent.putExtra("id_album", album1.getIdAlbum());
            intent.putExtra("name", album1.getNameAlbum());
            startActivity(intent);
        });
        recyclerViewAlbum.setAdapter(adapterAlbum);

        recyclerViewCate = view.findViewById(R.id.rcv_category);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCate.setLayoutManager(mLayoutManager);
        recyclerViewCate.setNestedScrollingEnabled(false);

        ArrayList<Category> listSortCate = new ArrayList<>();
        for (int i = 0; i < listCategories.size(); i++) {
            if (i < 5) {
                Category category = listCategories.get(i);
                listSortCate.add(category);
            }
        }
        adapterCate = new CategoryAdapter(getActivity(), listSortCate, (category1, position) -> {
            Intent intent = new Intent(getActivity(), ListSongActivity.class);
            intent.putExtra("id_category", category1.getIdCategory());
            intent.putExtra("name", category1.getNameCategory());
            startActivity(intent);
        });
        recyclerViewCate.setAdapter(adapterCate);

        title_playlist.setOnClickListener(view1 -> {
            sendIntent("PLAYLISTS", sPlayList);

        });
        title_album.setOnClickListener(view1 -> {
            sendIntent("ALBUMS", sAlbum);
        });
        title_category.setOnClickListener(view1 -> {
            sendIntent("CATEGORIES", sCate);
        });
    }

    private void sendIntent(String name, String gson) {
        Intent intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("gson", gson);
        startActivity(intent);
    }
}
