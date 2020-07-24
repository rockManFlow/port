package com.rock.port.test;

/**
 * Created by caoqingyuan on 2017/10/27.
 */
public interface UserInter {
    public void setName(String name);
    public void show();
    public void setAge(int age);
    public void setAll(String name,int age,String desc);
    public String getInfo();
}
