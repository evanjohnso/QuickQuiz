package com.evanrjohnso.quickquiz.models;


public class Sentence {
    private String word;
    private String text;

    public Sentence(String word, String text) {
        this.word = word;
        this.text = text;
    }
    public String getWord() { return word; }
    public String getInSentence() {
        return text;
    }
}
