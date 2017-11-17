package com.evanrjohnso.quickquiz;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.evanrjohnso.quickquiz.services.OxfordService;
import com.evanrjohnso.quickquiz.ui.DisplaySentencesFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import static org.junit.Assert.*;

public class OxfordServiceTest {
    private OxfordService oxford;
    @Before
    public void setUp() throws Exception {
        oxford = new OxfordService();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void grabSentence() throws Exception {
        oxford.grabSentence("hello", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("failed");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.printf("passed");
            }
        });
    }

    @Test
    public void getDefinition() throws Exception {
        oxford.getDefinitionFromOxford("destination");
    }

    @Test
    public void processAsyncResponse() throws Exception {

    }

}