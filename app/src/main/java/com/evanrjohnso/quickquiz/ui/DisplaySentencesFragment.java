package com.evanrjohnso.quickquiz.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.adapters.WordListAdapter;
import com.evanrjohnso.quickquiz.models.Sentence;
import com.evanrjohnso.quickquiz.services.OxfordService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DisplaySentencesFragment extends Fragment {
    public static OxfordService oxfordService;
    public static ArrayList<Sentence> sentencesList;
    private WordListAdapter mAdapter;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public DisplaySentencesFragment() { }  // Required empty public constructor

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_display_sentences, container, false);
        ButterKnife.bind(this, view);

        Intent intent = getActivity().getIntent();
        String word = intent.getStringExtra(Constants.WORD_KEY);
        oxfordService = new OxfordService();
        oxfordService.getSentenceFromOxford(word, sentenceCallback());
        return view;
    }

    private Callback sentenceCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sentencesList = oxfordService.processAsyncSentenceCall(response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new WordListAdapter(getActivity(), sentencesList);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(false);
                    }
                });
            }
        };
    }

//    private Callback definitionCallkback() {
//        return new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String asJSON = response.body().string();
//                System.out.println(asJSON);
//            }
//        };
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
