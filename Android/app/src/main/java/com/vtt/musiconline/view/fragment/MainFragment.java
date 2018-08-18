package com.vtt.musiconline.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.vtt.musiconline.view.ListSongActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MainFragment extends Fragment {
    PlaylistAdapter adapterPlaylist;
    AlbumAdapter adapterAlbum;
    CategoryAdapter adapterCate;
    RecyclerView recyclerViewPlayList, recyclerViewAlbum, recyclerViewCate;
    List<Playlist> listPlaylists = new ArrayList<>();
    List<Album> listAlbums = new ArrayList<>();
    List<Category> listCategories = new ArrayList<>();

    public MainFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerViewPlayList = view.findViewById(R.id.rcv_playlist);
        GridLayoutManager managerPlaylist = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewPlayList.setLayoutManager(managerPlaylist);
        recyclerViewPlayList.setNestedScrollingEnabled(false);

        recyclerViewAlbum = view.findViewById(R.id.rcv_album);
        GridLayoutManager managerAlbum = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewAlbum.setLayoutManager(managerAlbum);
        recyclerViewAlbum.setNestedScrollingEnabled(false);

        recyclerViewCate = view.findViewById(R.id.rcv_category);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCate.setLayoutManager(mLayoutManager);
        recyclerViewCate.setNestedScrollingEnabled(false);
        getPlaylist();
        getAlbum();
        getCategory();
        return view;
    }

    private void getPlaylist() {
        getActivity().runOnUiThread(() -> CircleProgressBar.getInstance(getActivity()).showProgress());
        (new GetPlayListTask(getActivity(),
                new GetPlayListTask.Listener() {
                    @Override
                    public void onSuccess(List<Playlist> playlists) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    listPlaylists = playlists;
                                    ArrayList<Playlist> listSort = new ArrayList<>();
                                    Playlist playlist = new Playlist();
                                    for (int i = 0; i < playlists.size(); i++) {
                                        if (i < 3) {
                                            playlist = playlists.get(i);
                                            listSort.add(playlist);
                                        }
                                    }
                                    adapterPlaylist = new PlaylistAdapter(getActivity(), listSort, new PlaylistAdapter.ItemClickListener() {
                                        @Override
                                        public void onItemPlayListClick(Playlist playlist, int position) {
                                            Intent intent = new Intent(getActivity(), ListSongActivity.class);
                                            intent.putExtra("id_playlist", playlist.getIdPlaylist());
                                            intent.putExtra("name", playlist.getNamePlaylist());
                                            startActivity(intent);
                                        }
                                    });
                                    recyclerViewPlayList.setAdapter(adapterPlaylist);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, "onFailure: " + message);
                        getActivity().runOnUiThread(() -> CircleProgressBar.getInstance(getActivity()).dismissProgress());
                    }
                })).execute();
    }

    private void getAlbum() {
        (new GetAlbumTask(getActivity(),
                new GetAlbumTask.Listener() {
                    @Override
                    public void onSuccess(List<Album> albums) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    listAlbums = albums;
                                    ArrayList<Album> listSort = new ArrayList<>();
                                    Album album = new Album();
                                    for (int i = 0; i < albums.size(); i++) {
                                        if (i < 3) {
                                            album = albums.get(i);
                                            listSort.add(album);
                                        }
                                    }
                                    adapterAlbum = new AlbumAdapter(getActivity(), listSort, new AlbumAdapter.ItemClickListener() {
                                        @Override
                                        public void onItemAlbumClick(Album album1, int position) {
                                            Intent intent = new Intent(getActivity(), ListSongActivity.class);
                                            intent.putExtra("id_album", album1.getIdAlbum());
                                            intent.putExtra("name", album1.getNameAlbum());
                                            startActivity(intent);
                                        }
                                    });
                                    recyclerViewAlbum.setAdapter(adapterAlbum);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, "onFailure: " + message);
                        getActivity().runOnUiThread(() -> CircleProgressBar.getInstance(getActivity()).dismissProgress());
                    }
                })).execute();
    }

    private void getCategory() {
        (new GetCategoryTask(getActivity(),
                new GetCategoryTask.Listener() {
                    @Override
                    public void onSuccess(List<Category> categories) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    getActivity().runOnUiThread(() -> CircleProgressBar.getInstance(getActivity()).dismissProgress());
                                    listCategories = categories;
                                    ArrayList<Category> listSort = new ArrayList<>();
                                    Category category = new Category();
                                    for (int i = 0; i < categories.size(); i++) {
                                        if (i < 5) {
                                            category = categories.get(i);
                                            listSort.add(category);
                                        }
                                    }
                                    adapterCate = new CategoryAdapter(getActivity(), listSort, new CategoryAdapter.ItemClickListener() {
                                        @Override
                                        public void onItemCateClick(Category category1, int position) {
                                            Intent intent = new Intent(getActivity(), ListSongActivity.class);
                                            intent.putExtra("id_category", category1.getIdCategory());
                                            intent.putExtra("name", category1.getNameCategory());
                                            startActivity(intent);
                                        }
                                    });
                                    recyclerViewCate.setAdapter(adapterCate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, "onFailure: " + message);
                        getActivity().runOnUiThread(() -> CircleProgressBar.getInstance(getActivity()).dismissProgress());
                    }
                })).execute();
    }
}
