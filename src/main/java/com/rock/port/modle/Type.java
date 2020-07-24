package com.rock.port.modle;

/**
 * Created by caoqingyuan on 2018/1/23.
 */
public enum Type {
    ALLOW("允许通过"),DEYN("拒绝");
    private String desc;

    public String getDesc() {
        return desc;
    }
    Type(String desc){
        this.desc=desc;
    }
}
