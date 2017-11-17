package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.services.OxfordService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WordDefinition extends AppCompatActivity {
    private OxfordService oxfordService;
    @Bind(R.id.wordTextView)
            TextView mWord;
    @Bind(R.id.entymology_list_view)
            ListView mEntymologyListView;
    @Bind(R.id.definitions_list_view)
            ListView mDefinitionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_definition);
        ButterKnife.bind(this);
        oxfordService = new OxfordService();
        Intent intent = getIntent();
        String word = intent.getStringExtra(Constants.WORD_KEY);
        oxfordService.getDefinitionFromOxford(word, definitionCallback());
    }

    private Callback definitionCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final HashMap<String, Object> definitionObject = oxfordService  .processAsyncDefinitionCall(response);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWord.setText( (String) definitionObject.get(Constants.WORD_KEY));
                        ArrayAdapter entymologyAdapter = new ArrayAdapter(WordDefinition.this, android.R.layout.simple_list_item_1, (ArrayList) definitionObject.get(Constants.ENTOMOLOGY));
                        ArrayAdapter definitionsAdapter = new ArrayAdapter(WordDefinition.this, android.R.layout.simple_list_item_1, (ArrayList) definitionObject.get(Constants.DEFINITIONS));

                        mEntymologyListView.setAdapter(entymologyAdapter);
                        mDefinitionsListView.setAdapter(definitionsAdapter);
                    }
                });
                Iterator it = definitionObject.entrySet().iterator();
                while (it.hasNext()) {
                    Log.v("outputs", it.next().toString());
                }
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
