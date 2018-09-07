package com.vtt.musiconline.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtt.musiconline.model.Album;
import com.vtt.musiconline.model.Category;
import com.vtt.musiconline.model.ListSong;
import com.vtt.musiconline.model.Playlist;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiManager {
    private static ApiManager apiManager;
    private MusicApi musicApi;

    public ApiManager(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unpeeled-toes.000webhostapp.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        musicApi = retrofit.create(MusicApi.class);
    }

    public static ApiManager getInstant(Context context){
        if (apiManager == null){
            apiManager = new ApiManager(context);
        }
        return apiManager;
    }

    public Call<List<ListSong>> listSongAll(){
        return musicApi.listSongAll();
    }
}
