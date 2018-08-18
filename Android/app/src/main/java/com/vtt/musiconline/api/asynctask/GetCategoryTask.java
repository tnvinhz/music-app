package com.vtt.musiconline.api.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.vtt.musiconline.api.ApiManager;
import com.vtt.musiconline.model.Category;
import com.vtt.musiconline.model.Playlist;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class GetCategoryTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "music";
    private GetCategoryTask.Listener callback;
    private Context context;

    public GetCategoryTask(Context context, GetCategoryTask.Listener callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            ApiManager apiManager = ApiManager.getInstant(context);
            Response<List<Category>> response = apiManager.category().execute();
            if (response.isSuccessful()) {
                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            } else {
                if (callback != null) {
                    callback.onFailure(response.code(), response.message());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface Listener {
        void onSuccess(List<Category> categories);

        void onFailure(int code, String message);
    }

}