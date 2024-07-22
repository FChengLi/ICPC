package com.example.icpc;

import android.os.Parcel;
import android.os.Parcelable;

public class DataItem implements Parcelable {
    private int videoId;
    private String title;
    private String description;
    private String author;
    private String filepath;
    private int coverpath;
    private int favoritenum;

    public int getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getFilepath() {
        return filepath;
    }

    public int getCoverpath() {
        return coverpath;
    }

    public int getFavoritenum() {
        return favoritenum;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setCoverpath(int coverpath) {
        this.coverpath = coverpath;
    }

    public void setFavoritenum(int favoritenum) {
        this.favoritenum = favoritenum;
    }

    // Parcelable 接口的实现代码
    protected DataItem(Parcel in) {
        videoId = in.readInt();
        title = in.readString();
        description = in.readString();
        author = in.readString();
        filepath = in.readString();
        coverpath = in.readInt();
        favoritenum = in.readInt();
    }

    public DataItem(){}

    public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel in) {
            return new DataItem(in);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(videoId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(author);
        dest.writeString(filepath);
        dest.writeInt(coverpath);
        dest.writeInt(favoritenum);
    }
}