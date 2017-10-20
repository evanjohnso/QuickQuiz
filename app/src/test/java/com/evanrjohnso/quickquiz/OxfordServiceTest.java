package com.evanrjohnso.quickquiz;

import com.evanrjohnso.quickquiz.services.OxfordService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
//        oxford.grabSentence("hello");
    }

    @Test
    public void processAsyncResponse() throws Exception {

    }

}