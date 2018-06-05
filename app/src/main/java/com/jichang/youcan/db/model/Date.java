package com.jichang.youcan.db.model;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class Date implements Serializable {

    private int id;

    private String date;

    public Date() {
    }

    public Date(int id, String time) {
        this.id = id;
        this.date = time;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
