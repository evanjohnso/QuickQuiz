package com.evanrjohnso.quickquiz.services;


import android.app.DownloadManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.evanrjohnso.quickquiz.Constants;

import java.util.concurrent.CopyOnWriteArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OxfordService {
    private static OkHttpClient client;
    private static HttpUrl.Builder urlBuilder;

    public void grabSentence(String inputWord) {
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
                .header("app_id", Constants.OXFORD_ID)
                .header("app_key", Constants.OXFORD_KEY)
                .build();
        Call call =  client.newCall(request);
        System.out.println(call.isExecuted());

//        call.enqueue(callback);
    }


}
