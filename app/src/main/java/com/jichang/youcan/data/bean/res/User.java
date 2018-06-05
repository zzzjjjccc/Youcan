package com.jichang.youcan.data.bean.res;

import java.io.Serializable;

/**
 * @author jichang
 * @date 2018/1/24
 * email: 2218982471@qq.com
 * describ:
 */

public class User implements Serializable {

    private String school;

    private String major;

    private String name;

    private String sex;

    public User() {
    }

    public User(String school, String major, String name, String sex) {
        this.school = school;
        this.major = major;
        this.name = name;
        this.sex = sex;
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
