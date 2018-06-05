package com.jichang.youcan.db.model;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class User implements Serializable {

    private int id;

    private String phone;

    private String password;

    private String school;

    private String major;

    private String name;

    private String sex;

    public User() {
    }

    public User(int id, String phone, String password, String school, String major, String name, String sex) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.school = school;
        this.major = major;
        this.name = name;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
