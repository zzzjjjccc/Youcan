package com.jichang.youcan.data.bean.req;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/24
 * email: 2218982471@qq.com
 * describ: Share request, Use Open and Content tables
 */

public class Share implements Serializable {

    private String phone;

    private String date;

    private String time;

    private String content;

    private int priority;

    private int done;

    private String open;

    public Share() {
    }

    public Share(String phone, String date, String time, String content, int priority, int done, String open) {
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.content = content;
        this.priority = priority;
        this.done = done;
        this.open = open;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getPriority() {
        return priority;
    }

    public int getDone() {
        return done;
    }

    public String getOpen() {
        return open;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setOpen(String open) {
        this.open = open;
    }

}
