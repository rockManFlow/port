package com.rock.port.test;

/**
 * Created by caoqingyuan on 2017/10/27.
 */
public class OldUserImpl implements UserInter {
    @Override
    public void setName(String name) {
        System.out.println("old setName:"+name);
    }

    @Override
    public void show() {
        System.out.println("old show");
    }

    @Override
    public void setAge(int age) {
        System.out.println("old setAge:"+age);
    }

    @Override
    public void setAll(String name, int age, String desc) {
        System.out.println("old setAll name:"+name+"|age:"+age+"|desc:"+desc);
    }

    @Override
    public String getInfo() {
        System.out.println("getInfo");
        return this.getClass().getName()+"|OK";
    }
}
