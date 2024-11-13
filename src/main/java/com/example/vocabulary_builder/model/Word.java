package com.example.vocabulary_builder.model;

public class Word {
    private Long id;
    private String word;
    private String definition;
    private String phoneticTranscription;
    private String exampleSentence;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPhoneticTranscription() {
        return phoneticTranscription;
    }

    public void setPhoneticTranscription(String phoneticTranscription) {
        this.phoneticTranscription = phoneticTranscription;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }
}
