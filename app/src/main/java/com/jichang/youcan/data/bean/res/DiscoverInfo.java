package com.jichang.youcan.data.bean.res;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class DiscoverInfo implements Serializable {

    private String name;

    private String date;

    private String content;

    public DiscoverInfo() {
    }

    public DiscoverInfo(String name, String date, String content) {
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
