package com.evanrjohnso.quickquiz.services;


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
import java.util.Map;

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
                .addPathSegment(Constants.ENGLISH_LANGUAGE)
                .addPathSegment(inputWord)
                .addPathSegment(Constants.SENTENCES);
        String requestUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("app_id", Constants.OXFORD_ID)
                .addHeader("app_key", Constants.OXFORD_KEY)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void getDefinitionFromOxford(String inputWord) {
        client = new OkHttpClient.Builder().build();

        urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder()
                .addPathSegment(Constants.ENTRIES)
                .addPathSegment(Constants.ENGLISH_LANGUAGE)
                .addPathSegment(inputWord);
        String requestUrl = urlBuilder.build().toString();
        System.out.println(requestUrl);

        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("app_id", Constants.OXFORD_ID)
                .addHeader("app_key", Constants.OXFORD_KEY)
                .build();
        Call call = client.newCall(request);
        call.enqueue(definitionCallback());
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

    private Callback definitionCallback() {
        System.out.println("callback");
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("failed");
//                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("passed");
//                String asJSON = response.body().string();
//                System.out.println(asJSON);
            }
        };
    }


}