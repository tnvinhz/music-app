package com.vtt.musiconline.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vtt.musiconline.R;
import com.vtt.musiconline.adapter.ListSongAdapter;
import com.vtt.musiconline.api.asynctask.PostListSongAllTask;
import com.vtt.musiconline.model.ListSong;
import com.vtt.musiconline.utils.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;

import static android.support.constraint.Constraints.TAG;

public class ListSongActivity extends AppCompatActivity {
    EditText editTextSearch;
    RelativeLayout title_play, rl_search;
    ListSongAdapter songAdapter;
    RecyclerView recyclerViewListSong;
    List<ListSong> songList = new ArrayList<>();
    List<ListSong> intentList = new ArrayList<>();
    ArrayList<ListSong> filteredList = new ArrayList<>();
    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        initView();
    }
    //ánh xạ cac view
    private void initView() {
        rl_search = findViewById(R.id.rl_search);
        title_play = findViewById(R.id.title_play);
        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerViewListSong = findViewById(R.id.rcv_list_song);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewListSong.setLayoutManager(mLayoutManager);
        recyclerViewListSong.setNestedScrollingEnabled(false);
        //dialog mất mạng
        noInternetDialog = new NoInternetDialog.Builder(ListSongActivity.this).build();
        noInternetDialog.setOnDismissListener(dialogInterface -> {
            //khi có mạng thì load lại api
            if (songList.size() == 0) getAllSong();
        });
        if (songList.size() == 0) getAllSong();
    }
    // lọc từ tìm kiếm
    private void filter(String text) {
        filteredList.clear();
        for (ListSong item : songList) {
            // add item trùng với từ tìm kiếm vào list
            if (item.getNameSong().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (text.equals("")) {
            //all add lại tất cả item khi không tìm kiếm nữa
            filteredList.clear();
            filteredList.addAll(songList);
        }
        songAdapter.filterList(filteredList);
    }

    @Override
    protected void onPause() {
        runOnUiThread(() -> {
            if (CircleProgressBar.getInstance(this).isShowing()) {
                CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress();
            }
        });
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }

    // lấy dữ liệu bài hát từ api
    private void getAllSong() {
        runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).showProgress());
        (new PostListSongAllTask(ListSongActivity.this,
                new PostListSongAllTask.Listener() {
                    @Override
                    public void onSuccess(List<ListSong> listSongs) {
                        runOnUiThread(() -> {
                            try {
                                runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress());
                                songList = listSongs;
                                //truyền list bài hát lấy từ api về vào adapter của listview
                                songAdapter = new ListSongAdapter(ListSongActivity.this, songList, listSong -> {
                                    //Truyền bài hát sang màn phát nhạc khi click vào bài hát
                                    runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).showProgress());
                                    Intent intent = new Intent(ListSongActivity.this, PlayActivity.class);
                                    intentList.clear();
                                    for (int i = 0; i <songList.size(); i++){
                                        if(listSong.getNameSong().equals(songList.get(i).getNameSong())){
                                            songList.get(i).setPlay(true);
                                        } else {
                                            songList.get(i).setPlay(false);
                                        }
                                    }
                                    intentList.addAll(songList);
                                    Gson gson = new Gson();
                                    String playSearch = gson.toJson(intentList);
                                    intent.putExtra("listSongPlay", playSearch);
                                    startActivity(intent);
                                });
                                // gán adapter vào listview để hiển thị các bài hát
                                recyclerViewListSong.setAdapter(songAdapter);
                                // truyền list bài hát sang màn phát nhạc khi click vào phát tất cả
                                title_play.setOnClickListener(view -> {
                                    Intent intent = new Intent(ListSongActivity.this, PlayActivity.class);
                                    intentList.clear();
                                    intentList.addAll(songList);
                                    Gson gson = new Gson();
                                    String playSearch = gson.toJson(intentList);
                                    intent.putExtra("listSongPlay", playSearch);
                                    startActivity(intent);
                                });
                                // bắt thay đổi của chữ trong edittext search
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, "onFailure: " + message);
                        runOnUiThread(() -> CircleProgressBar.getInstance(ListSongActivity.this).dismissProgress());
                    }
                })).execute();
    }

}

