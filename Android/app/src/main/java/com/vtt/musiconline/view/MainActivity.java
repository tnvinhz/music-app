package com.vtt.musiconline.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.vtt.musiconline.R;
import com.vtt.musiconline.adapter.AlbumAdapter;
import com.vtt.musiconline.adapter.CategoryAdapter;
import com.vtt.musiconline.adapter.PlaylistAdapter;
import com.vtt.musiconline.api.asynctask.GetAlbumTask;
import com.vtt.musiconline.api.asynctask.GetCategoryTask;
import com.vtt.musiconline.api.asynctask.GetPlayListTask;
import com.vtt.musiconline.api.asynctask.PostListSongAllTask;
import com.vtt.musiconline.model.Album;
import com.vtt.musiconline.model.Category;
import com.vtt.musiconline.model.ListSong;
import com.vtt.musiconline.model.Playlist;
import com.vtt.musiconline.utils.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity{
    String gsonListSong;
    LinearLayout ll_search;
    NoInternetDialog noInternetDialog;
    PlaylistAdapter adapterPlaylist;
    AlbumAdapter adapterAlbum;
    CategoryAdapter adapterCate;
    RecyclerView recyclerViewPlayList, recyclerViewAlbum, recyclerViewCate;
    List<Playlist> playlist = new ArrayList<>();
    List<Album> album = new ArrayList<>();
    List<Category> category = new ArrayList<>();
    List<ListSong> listSong = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        noInternetDialog = new NoInternetDialog.Builder(MainActivity.this).build();
        noInternetDialog.setOnDismissListener(dialogInterface -> {
            if (listSong.size() == 0) getAllSong();
            if (playlist.size() == 0) getPlaylist();
            if (album.size() == 0) getAlbum();
            if (category.size() == 0) getCategory();
        });
        if (listSong.size() == 0) getAllSong();
        if (playlist.size() == 0) getPlaylist();
        if (album.size() == 0) getAlbum();
        if (category.size() == 0) getCategory();
        initView();

    }

    private void initView() {
        ll_search = findViewById(R.id.ll_search);
        ll_search.setOnClickListener(view -> {
            //truyền dữ liệu sang màn search
            Intent intent = new Intent(MainActivity.this, ListSongActivity.class);
            intent.putExtra("type", "search");
            intent.putExtra("gsonlistSong", gsonListSong);
            startActivity(intent);
        });
    }

    // lấy dữ liệu search từ api
    private void getAllSong() {
        (new PostListSongAllTask(MainActivity.this,
                new PostListSongAllTask.Listener() {
                    @Override
                    public void onSuccess(List<ListSong> listSongs) {
                        runOnUiThread(() -> {
                            try {
                                runOnUiThread(() -> CircleProgressBar.getInstance(MainActivity.this).dismissProgress());
                                listSong = listSongs;
                                Gson gson = new Gson();
                                gsonListSong = gson.toJson(listSongs);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, "onFailure: " + message);
                        runOnUiThread(() -> CircleProgressBar.getInstance(MainActivity.this).dismissProgress());
                    }
                })).execute();
    }

    // lấy dữ liệu playlist từ api
    private void getPlaylist() {
        runOnUiThread(() -> CircleProgressBar.getInstance(MainActivity.this).showProgress());
        (new GetPlayListTask(MainActivity.this,
                new GetPlayListTask.Listener() {
                    @Override
                    public void onSuccess(List<Playlist> playlists) {
                        runOnUiThread(() -> {
                            try {
                                playlist = playlists;
                                recyclerViewPlayList = findViewById(R.id.rcv_playlist);
                                GridLayoutManager managerPlaylist = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
                                recyclerViewPlayList.setLayoutManager(managerPlaylist);
                                adapterPlaylist = new PlaylistAdapter(MainActivity.this, playlists, (playlist) -> {
                                    //truyền dữ liệu (playlist) sang màn danh sách bài hát
                                    Intent intent = new Intent(MainActivity.this, ListSongActivity.class);
                                    intent.putExtra("id_playlist", playlist.getIdPlaylist());
                                    intent.putExtra("name", playlist.getNamePlaylist());
                                    intent.putExtra("type", "main");
                                    startActivity(intent);
                                });
                                recyclerViewPlayList.setAdapter(adapterPlaylist);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, "onFailure: " + message);
                        runOnUiThread(() -> CircleProgressBar.getInstance(MainActivity.this).dismissProgress());
                    }
                })).execute();
    }

    // lấy dữ liệu album từ api
    private void getAlbum() {
        (new GetAlbumTask(MainActivity.this,
                new GetAlbumTask.Listener() {
                    @Override
                    public void onSuccess(List<Album> albums) {
                        runOnUiThread(() -> {
                            try {
                                album = albums;
                                recyclerViewAlbum = findViewById(R.id.rcv_album);
                                GridLayoutManager managerAlbum = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
                                recyclerViewAlbum.setLayoutManager(managerAlbum);
                                adapterAlbum = new AlbumAdapter(MainActivity.this, albums, (album1) -> {
                                    //truyền dữ liệu (album) sang màn danh sách bài hát
                                    Intent intent = new Intent(MainActivity.this, ListSongActivity.class);
                                    intent.putExtra("id_album", album1.getIdAlbum());
                                    intent.putExtra("name", album1.getNameAlbum());
                                    intent.putExtra("type", "main");
                                    startActivity(intent);
                                });
                                recyclerViewAlbum.setAdapter(adapterAlbum);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, "onFailure: " + message);
                        runOnUiThread(() -> CircleProgressBar.getInstance(MainActivity.this).dismissProgress());
                    }
                })).execute();
    }

    // lấy dữ liệu category từ api
    private void getCategory() {
        (new GetCategoryTask(MainActivity.this,
                new GetCategoryTask.Listener() {
                    @Override
                    public void onSuccess(List<Category> categories) {
                        runOnUiThread(() -> {
                            try {
                                category = categories;
                                recyclerViewCate = findViewById(R.id.rcv_category);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                                recyclerViewCate.setLayoutManager(mLayoutManager);
                                recyclerViewCate.setNestedScrollingEnabled(false);
                                adapterCate = new CategoryAdapter(MainActivity.this, categories, (category1) -> {
                                    //truyền dữ liệu (category) sang màn danh sách bài hát
                                    Intent intent = new Intent(MainActivity.this, ListSongActivity.class);
                                    intent.putExtra("id_category", category1.getIdCategory());
                                    intent.putExtra("name", category1.getNameCategory());
                                    intent.putExtra("type", "main");
                                    startActivity(intent);
                                });
                                recyclerViewCate.setAdapter(adapterCate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, "onFailure: " + message);
                        runOnUiThread(() -> CircleProgressBar.getInstance(MainActivity.this).dismissProgress());
                    }
                })).execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}
