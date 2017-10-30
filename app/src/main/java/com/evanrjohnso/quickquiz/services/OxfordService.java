package com.evanrjohnso.quickquiz.services;


import android.app.DownloadManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.models.Sentence;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    private DatabaseReference firebaseDatabase;

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
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Sentence> processAsyncResponse(Response response) {
        ArrayList<Sentence> sentences = new ArrayList<>();
        try {
            String responseAsJSON = response.body().string();
            JSONObject json = new JSONObject(responseAsJSON);

            JSONObject results = (JSONObject) json.getJSONArray("results")
                    .get(0);
            String word = results.getString("id");
            JSONObject sentenceObject = (JSONObject) results.getJSONArray("lexicalEntries").get(0);
            JSONArray array = sentenceObject.getJSONArray("sentences");
            for (int i = 0; i < array.length(); i++) {
                String thisSentence = ((JSONObject) array.get(i)).getString("text");
                sentences.add(new Sentence(word, thisSentence));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        saveSentencesToFirebase(sentences);
        return sentences;
    }

    private void saveSentencesToFirebase(ArrayList<Sentence> sentences ) {
        String word = sentences.get(0).getWord();
        firebaseDatabase = FirebaseDatabase.getInstance()
                .getReference();
        Map<String, Object> wordMap = new HashMap<>();
        wordMap.put(word,word);
        firebaseDatabase.child(Constants.WORDS).updateChildren(wordMap); // add word to words node
        firebaseDatabase.child(Constants.SENTENCES).child(word).setValue(Arrays.asList(sentences));
    }


}