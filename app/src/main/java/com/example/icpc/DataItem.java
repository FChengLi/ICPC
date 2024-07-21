package com.example.icpc;
public class DataItem {
    private String imageUrl;
    private String source;
    private String title;

    public DataItem(String imageUrl, String source, String title) {
        this.imageUrl = imageUrl;
        this.source = source;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
