package com.vtt.musiconline.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vtt.musiconline.R;
import com.vtt.musiconline.adapter.AlbumAdapter;
import com.vtt.musiconline.adapter.CategoryAdapter;
import com.vtt.musiconline.adapter.PlaylistAdapter;
import com.vtt.musiconline.model.Album;
import com.vtt.musiconline.model.Category;
import com.vtt.musiconline.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RelativeLayout rl_play_all;
    TextView tv_name;
    ImageView btn_back;
    PlaylistAdapter adapterPlaylist;
    AlbumAdapter adapterAlbum;
    CategoryAdapter adapterCate;
    RecyclerView recyclerViewGroup;
    String name, gson;
    List<Playlist> listPlaylists = new ArrayList<>();
    List<Album> listAlbums = new ArrayList<>();
    List<Category> listCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        name = getIntent().getStringExtra("name");
        gson = getIntent().getStringExtra("gson");
        switch (name){
            case("PLAYLISTS"):{
                TypeToken<List<Playlist>> token = new TypeToken<List<Playlist>>() {};
                listPlaylists = new Gson().fromJson(gson, token.getType());
                break;
            }
            case("ALBUMS"):{
                TypeToken<List<Album>> token = new TypeToken<List<Album>>() {};
                listAlbums = new Gson().fromJson(gson, token.getType());
                break;
            }
            case("CATEGORIES"):{
                TypeToken<List<Category>> token = new TypeToken<List<Category>>() {};
                listCategories = new Gson().fromJson(gson, token.getType());
                break;
            }
        }
        initView();
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        rl_play_all = findViewById(R.id.title_play);
        tv_name = findViewById(R.id.tv_name_group);
        if (name != null) tv_name.setText(name);
        recyclerViewGroup = findViewById(R.id.rcv_group);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerViewGroup.setLayoutManager(manager);
        recyclerViewGroup.setNestedScrollingEnabled(false);
        switch (name){
            case("playlist"):{
                adapterPlaylist = new PlaylistAdapter(this, listPlaylists, (playlist, position) -> {
                    Intent intent = new Intent(this, ListSongActivity.class);
                    intent.putExtra("id_playlist", playlist.getIdPlaylist());
                    intent.putExtra("name", playlist.getNamePlaylist());
                    startActivity(intent);
                });
                recyclerViewGroup.setAdapter(adapterPlaylist);
                break;
            }
            case("album"):{
                adapterAlbum = new AlbumAdapter(this, listAlbums, (album1, position) -> {
                    Intent intent = new Intent(this, ListSongActivity.class);
                    intent.putExtra("id_album", album1.getIdAlbum());
                    intent.putExtra("name", album1.getNameAlbum());
                    startActivity(intent);
                });
                recyclerViewGroup.setAdapter(adapterAlbum);
                break;
            }
            case("category"):{
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerViewGroup.setLayoutManager(mLayoutManager);
                adapterCate = new CategoryAdapter(this, listCategories, (category1, position) -> {
                    Intent intent = new Intent(this, ListSongActivity.class);
                    intent.putExtra("id_category", category1.getIdCategory());
                    intent.putExtra("name", category1.getNameCategory());
                    startActivity(intent);
                });
                recyclerViewGroup.setAdapter(adapterCate);
                break;
            }
        }
        btn_back.setOnClickListener(view -> finish());
    }


}

