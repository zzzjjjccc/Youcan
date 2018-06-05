package com.jichang.youcan.db.model;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class Open implements Serializable {

    private int id;

    private String date;

    private String open;

    public Open() {
    }

    public Open(int id, String date, String open) {
        this.id = id;
        this.date = date;
        this.open = open;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getOpen() {
        return open;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOpen(String open) {
        this.open = open;
    }

}
