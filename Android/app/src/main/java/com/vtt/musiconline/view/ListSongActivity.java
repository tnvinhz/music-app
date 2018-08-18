package com.vtt.musiconline.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vtt.musiconline.R;
import com.vtt.musiconline.adapter.ListSongAdapter;
import com.vtt.musiconline.api.asynctask.PostListSongAlbumTask;
import com.vtt.musiconline.api.asynctask.PostListSongCateTask;
import com.vtt.musiconline.api.asynctask.PostListSongPlaylistTask;
import com.vtt.musiconline.model.ListSong;
import com.vtt.musiconline.model.Playlist;
import com.vtt.musiconline.utils.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

public class ListSongActivity extends AppCompatActivity {
    String id_playlist, id_album, id_category, type,gsonlistSong;
    String name = "Group";
    EditText editTextSearch;
    LinearLayout ll_bar;
    RelativeLayout title_play,rl_search;
    TextView tv_name;
    ImageView btn_back, btn_back_search;
    ListSongAdapter songAdapter;
    RecyclerView recyclerViewListSong;
    List<ListSong> songList  = new ArrayList<>();
    ArrayList<ListSong> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        id_playlist = getIntent().getStringExtra("id_playlist");
        id_album = getIntent().getStringExtra("id_album");
        id_category = getIntent().getStringExtra("id_category");
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        gsonlistSong = getIntent().getStringExtra("gsonlistSong");
        initView();
    }

    private void initView() {
        ll_bar = findViewById(R.id.ll_bar);
        rl_search = findViewById(R.id.rl_search);
        title_play = findViewById(R.id.title_play);
        tv_name = findViewById(R.id.tv_name_group);
        btn_back = findViewById(R.id.btn_back);
        btn_back_search = findViewById(R.id.btn_back_search);
        editTextSearch = findViewById(R.id.editTextSearch);

        recyclerViewListSong = findViewById(R.id.rcv_list_song);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewListSong.setLayoutManager(mLayoutManager);
        recyclerViewListSong.setNestedScrollingEnabled(false);
        if (name != null) tv_name.setText(name);
        if(id_playlist!= null) getListSongPlayList(id_playlist);
        if(id_album!= null) getListSongAlbum(id_album);
        if(id_category!= null) getListSongCate(id_category);

        btn_back.setOnClickListener(view -> finish());
        btn_back_search.setOnClickListener(view -> finish());
        if(type.equals("search")){
            TypeToken<List<ListSong>> token = new TypeToken<List<ListSong>>() {};
            songList = new Gson().fromJson(gsonlistSong, token.getType());
            songAdapter = new ListSongAdapter(ListSongActivity.this, filteredList, new ListSongAdapter.ItemClickListener() {
                @Override
                public void onItemSongClick(ListSong listSong, int position) {
                    Toast.makeText(ListSongActivity.this, "id" + listSong.getNameSong(), Toast.LENGTH_SHORT).show();
                }
            });
            recyclerViewListSong.setAdapter(songAdapter);
            rl_search.setVisibility(View.VISIBLE);
            ll_bar.setVisibility(View.GONE);
            title_play.setVisibility(View.GONE);
            editTextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
    private void filter(String text) {
        filteredList.clear();
        for (ListSong item : songList) {
            if (item.getNameSong().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if(text.equals("")){
            filteredList.clear();
        }
        songAdapter.filterList(filteredList);
    }

    private void getListSongPlayList(String id_playlist) {
        runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).showProgress());
        (new PostListSongPlaylistTask(this, id_playlist,
                new PostListSongPlaylistTask.Listener() {
                    @Override
                    public void onSuccess(List<ListSong> listSongs) {
                        runOnUiThread(() -> {
                            try {
                                runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress());
                                songList = listSongs;
                                songAdapter = new ListSongAdapter(ListSongActivity.this, songList, new ListSongAdapter.ItemClickListener() {
                                    @Override
                                    public void onItemSongClick(ListSong listSong, int position) {
                                        Toast.makeText(ListSongActivity.this, "id" + listSong.getNameSong(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                recyclerViewListSong.setAdapter(songAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d("music", "onFailure: " + message);
                        runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress());
                    }
                })).execute();
    }

    private void getListSongAlbum(String id_album) {
        runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).showProgress());
        (new PostListSongAlbumTask(this, id_album,
                new PostListSongAlbumTask.Listener() {
                    @Override
                    public void onSuccess(List<ListSong> listSongs) {
                        runOnUiThread(() -> {
                            try {
                                runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress());
                                songList = listSongs;
                                songAdapter = new ListSongAdapter(ListSongActivity.this, songList, new ListSongAdapter.ItemClickListener() {
                                    @Override
                                    public void onItemSongClick(ListSong listSong, int position) {
                                        Toast.makeText(ListSongActivity.this, "id" + listSong.getNameSong(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                recyclerViewListSong.setAdapter(songAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d("music", "onFailure: " + message);
                        runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress());
                    }
                })).execute();
    }

    private void getListSongCate(String id_category) {
        runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).showProgress());
        (new PostListSongCateTask(this, id_category,
                new PostListSongCateTask.Listener() {
                    @Override
                    public void onSuccess(List<ListSong> listSongs) {
                        runOnUiThread(() -> {
                            try {
                                runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress());
                                songList = listSongs;
                                songAdapter = new ListSongAdapter(ListSongActivity.this, songList, new ListSongAdapter.ItemClickListener() {
                                    @Override
                                    public void onItemSongClick(ListSong listSong, int position) {
                                        Toast.makeText(ListSongActivity.this, "id" + listSong.getNameSong(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                recyclerViewListSong.setAdapter(songAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d("music", "onFailure: " + message);
                        runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress());
                    }
                })).execute();
    }
}

