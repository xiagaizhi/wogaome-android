package com.yushi.leke.fragment.ucenter;

import java.io.Serializable;

/**
 * 作者：Created by zhanyangyang on 2018/9/4 20:36
 * 邮箱：zhanyangyang@hzyushi.cn
 * <p>
 * 获取我的个人基本信息 ：自己编辑的信息
 */

public class MyBaseInfo implements Serializable{
    private String avatar;
    private String userName;
    private String gender;
    private String company;
    private String position;
    private String motto;
    private String email;
    private String city;
    private String address;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
