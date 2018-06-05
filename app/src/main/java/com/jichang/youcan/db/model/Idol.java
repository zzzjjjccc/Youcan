package com.jichang.youcan.db.model;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class Idol implements Serializable {

    private int id;

    private String idol;

    private String phone;

    public Idol() {
    }

    public Idol(int id, String idol, String phone) {
        this.id = id;
        this.idol = idol;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getIdol() {
        return idol;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdol(String idol) {
        this.idol = idol;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
