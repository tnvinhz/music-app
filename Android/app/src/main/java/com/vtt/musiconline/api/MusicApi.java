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

    @FormUrlEncoded
    @POST("server/api_list_song.php")
    Call<List<ListSong>> listSongPlaylist(
            @Field("id_playlist") String id_playlist
    );

    @FormUrlEncoded
    @POST("server/api_list_song.php")
    Call<List<ListSong>> listSongAlbum(
            @Field("id_album") String id_album
    );

    @FormUrlEncoded
    @POST("server/api_list_song.php")
    Call<List<ListSong>> listSongCate(
            @Field("id_category") String id_cate
    );

    @FormUrlEncoded
    @POST("server/api_list_song.php")
    Call<List<ListSong>> listSongAll(
    );

    @GET("server/api_playlist.php")
    Call<List<Playlist>> playlist();

    @GET("server/api_album.php")
    Call<List<Album>> album();

    @GET("server/api_category.php")
    Call<List<Category>> catogory();

}
