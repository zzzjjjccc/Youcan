package com.jichang.youcan.data.bean.req;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/24
 * email: 2218982471@qq.com
 * describ: Subscribe request, according to phone number, query phone number and date from open table (open word),
 * use result to query content from
 * Content table
 * Also can use this request Fan and Idols, because all of them just use phone number to query
 */

public class Subscribe implements Serializable {

    private String phone;

    public Subscribe() {
    }

    public Subscribe(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
