package com.vtt.musiconline.api;

import com.vtt.musiconline.model.Album;
import com.vtt.musiconline.model.Category;
import com.vtt.musiconline.model.ListSong;
import com.vtt.musiconline.model.Playlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface MusicApi {
    @GET("server/api_list_song.php")
    Call<List<ListSong>> listSongAll(
    );
}
