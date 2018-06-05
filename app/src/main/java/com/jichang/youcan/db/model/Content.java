package com.jichang.youcan.db.model;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class Content implements Serializable{

    private int id;

    private String date;

    private String time;

    private String content;

    private int priority;

    private int done;

    public Content() {
    }

    public Content(int id, String date, String time,
                   String content, int priority, int done) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.content = content;
        this.priority = priority;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

    public int getDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDone(int done) {
        this.done = done;
    }
}
