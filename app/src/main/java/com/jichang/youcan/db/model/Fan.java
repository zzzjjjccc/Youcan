package com.jichang.youcan.db.model;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class Fan implements Serializable {

    private int id;

    private String phone;

    private String fan;

    public Fan() {
    }

    public Fan(int id, String phone, String fan) {
        this.id = id;
        this.phone = phone;
        this.fan = fan;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getFan() {
        return fan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }

}
