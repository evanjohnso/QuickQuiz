package com.evanrjohnso.quickquiz.services;


import android.app.DownloadManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.models.Sentence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OxfordService {
    private static OkHttpClient client;
    private static HttpUrl.Builder urlBuilder;

    public void grabSentence(String inputWord, Callback callback) {
        client = new OkHttpClient.Builder().build();

        urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder()
                .addPathSegment(Constants.ENTRIES)
                .addPathSegment(Constants.LANGAUGE)
                .addPathSegment(inputWord)
                .addPathSegment(Constants.SENTENCES);
        String requestUrl = urlBuilder.build().toString();
        Log.v("My RequestUrlBuggHuntin", requestUrl);

        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("app_id", Constants.OXFORD_ID)
                .addHeader("app_key", Constants.OXFORD_KEY)
                .build();
        Call call =  client.newCall(request);
        call.enqueue(callback);
    }

    public void processAsyncResponse(Response response) {
        ArrayList<Sentence> sentences = new ArrayList<>();
        System.out.println("got this far mates");
        try {
            String responseAsJSON = response.body().string();
            Log.v("responseAsJson", responseAsJSON);
            JSONObject json = new JSONObject(responseAsJSON);
            JSONArray results = json.getJSONArray("results");
            System.out.println(results);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }



}