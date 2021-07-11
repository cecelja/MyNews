package com.example.mynews;

public class DataModel {
    private String title;
    private String url;

    public DataModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
