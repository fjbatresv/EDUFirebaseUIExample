package edu.edutec.fjbatresv.firebaseuiexample;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by javie on 4/21/2018.
 */
@IgnoreExtraProperties
public class Post implements Serializable {
    private String text;
    private long time;

    public Post() {
    }

    public Post(String text, long time) {
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
