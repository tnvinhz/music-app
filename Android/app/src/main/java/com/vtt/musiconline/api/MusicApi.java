package com.vtt.musiconline.api;

import com.vtt.musiconline.model.ListSong;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface MusicApi {
    @GET("server/api_list_song.php")
    Call<List<ListSong>> listSongAll(
    );
}
