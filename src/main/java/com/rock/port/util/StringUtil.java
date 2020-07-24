package com.rock.port.util;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by caoqingyuan on 2017/10/18.
 */
public class StringUtil {
    public static Boolean regularVerify(String... st){
        //yyyy-MM-dd验证规则
        String regular="^[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}";
        for(String input:st){
            if(!Pattern.matches(regular,input)){
                return false;
            }
        }
        return true;
    }

    //判断是否为空--全部不为空
    public static Boolean isNotEmpty(String... st){
        for(String input:st){
            if(null==input||"".equals(input)){
                return false;
            }
        }
        return true;
    }

    //不能全部为空
    public static Boolean mutexNotEmpty(String... st){
        for(String input:st){
            if(null!=input&&!"".equals(input)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){

    }

    /**
     * 比较end是否大于start
     * @param start 开始时间yyyy-MM-dd
     * @param end 结束时间yyyy-MM-dd
     * @return
     */
    public static Boolean compareA2B(String start,String end){
        int startDate=Integer.parseInt(start.replaceAll("-",""));
        int endDate=Integer.parseInt(end.replaceAll("-",""));
        if(endDate>=startDate){
            return true;
        }
        return false;
    }

    @Test
    public void test(){
        String a="2017-09-10";
        String b="2017-08-29";
        System.out.println("result:"+compareA2B(a,b));
    }

}
