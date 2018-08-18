package com.vtt.musiconline.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.vtt.musiconline.R;
import com.vtt.musiconline.api.asynctask.GetAlbumTask;
import com.vtt.musiconline.api.asynctask.GetCategoryTask;
import com.vtt.musiconline.api.asynctask.GetPlayListTask;
import com.vtt.musiconline.api.asynctask.PostListSongAllTask;
import com.vtt.musiconline.model.Album;
import com.vtt.musiconline.model.Category;
import com.vtt.musiconline.model.ListSong;
import com.vtt.musiconline.model.Playlist;
import com.vtt.musiconline.utils.CircleProgressBar;
import com.vtt.musiconline.view.fragment.FavFragment;
import com.vtt.musiconline.view.fragment.MainFragment;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity {
    String gsonAlbum, gsonPlaylist, gsonCate, gsonListSong;
    LinearLayout ll_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPlaylist();
        getAlbum();
        getCategory();
        getAllSong();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        ll_search = findViewById(R.id.ll_search);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            addFragment(MainFragment.newInstance(gsonPlaylist,gsonAlbum,gsonCate));
                            break;
                        case R.id.navigation_fav:
                            addFragment(FavFragment.newInstance());
                            break;
                    }
                    return true;
                });
        ll_search.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,ListSongActivity.class);
            intent.putExtra("type","search");
            intent.putExtra("gsonlistSong", gsonListSong);
            startActivity(intent);
        });

    }

    private void addFragment( Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_screen, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void getAllSong() {
        (new PostListSongAllTask(MainActivity.this,
                new PostListSongAllTask.Listener() {
                    @Override
                    public void onSuccess(List<ListSong> listSongs) {
                        runOnUiThread(() -> {
                            try {
                                runOnUiThread(() -> CircleProgressBar.getInstance(MainActivity.this).dismissProgress());
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

    private void getPlaylist() {
        runOnUiThread(() -> CircleProgressBar.getInstance(MainActivity.this).showProgress());
        (new GetPlayListTask(MainActivity.this,
                new GetPlayListTask.Listener() {
                    @Override
                    public void onSuccess(List<Playlist> playlists) {
                        runOnUiThread(() -> {
                            try {
                                Gson gson = new Gson();
                                gsonPlaylist = gson.toJson(playlists);
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

    private void getAlbum() {
        (new GetAlbumTask(MainActivity.this,
                new GetAlbumTask.Listener() {
                    @Override
                    public void onSuccess(List<Album> albums) {
                        runOnUiThread(() -> {
                            try {
                                Gson gson = new Gson();
                                gsonAlbum = gson.toJson(albums);
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

    private void getCategory() {
        (new GetCategoryTask(MainActivity.this,
                new GetCategoryTask.Listener() {
                    @Override
                    public void onSuccess(List<Category> categories) {
                        runOnUiThread(() -> {
                            try {
                                Gson gson = new Gson();
                                gsonCate = gson.toJson(categories);
                                addFragment(MainFragment.newInstance(gsonPlaylist,gsonAlbum,gsonCate));
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
}
