package com.jichang.youcan.data.bean.req;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/24
 * email: 2218982471@qq.com
 * describ: Register request, Just use User table
 */

public class Register implements Serializable {

    private String phone;

    private String password;

    public Register() {
    }

    public Register(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
