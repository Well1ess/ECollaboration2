package com.example.a29149.ecollaboration.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 29149 on 2017/3/16.
 */

public class MyMessage implements Parcelable {

    private String title;
    private String content;
    private String creator;

    public MyMessage() {
    }

    private MyMessage(Parcel in)
    {
        this.title=in.readString();
        this.content=in.readString();
        this.creator=in.readString();
    }

    public MyMessage(String title, String content, String creator) {
        this.title = title;
        this.content = content;
        this.creator = creator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.creator);
    }

    public static final Creator<MyMessage> CREATOR =
            new Creator<MyMessage>()
            {
                @Override
                public MyMessage createFromParcel(Parcel source) {
                    return new MyMessage(source);
                }

                @Override
                public MyMessage[] newArray(int size) {
                    return new MyMessage[0];
                }
            };

    @Override
    public String toString() {
        return title+content+creator;
    }
}
