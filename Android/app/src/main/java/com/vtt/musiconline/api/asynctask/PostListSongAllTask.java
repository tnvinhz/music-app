package com.vtt.musiconline.api.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.vtt.musiconline.api.ApiManager;
import com.vtt.musiconline.model.ListSong;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class PostListSongAllTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "music";
    private Listener callback;
    private Context context;

    public PostListSongAllTask(Context context, Listener callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            ApiManager apiManager = ApiManager.getInstant(context);
            Response<List<ListSong>> response = apiManager.listSongAll().execute();
            if(response.isSuccessful()){
                if(callback!=null){
                    callback.onSuccess(response.body());
                }
            }else{
                if(callback!=null){
                    callback.onFailure(response.code(), response.message());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface Listener {
        void onSuccess(List<ListSong> listSongsAll);
        void onFailure(int code, String message);
    }
}