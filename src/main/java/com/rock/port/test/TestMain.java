package com.rock.port.test;

import com.rock.port.util.URLConnection;

/**
 * Created by caoqingyuan on 2017/10/17.
 */
public class TestMain {
    //已实现负载均衡
    public static void testZuul() throws Exception {
        for(int i=0;i<50;i++) {
            new Thread(new ZuulTest(i)).start();
        }
    }

    public static void main(String[] args) throws Exception {
//        testZuul();
        reqUrl();
    }
    public static void reqUrl() throws Exception {
        System.setProperty("javax.net.ssl.trustStore","D:\\myProjects\\port\\src\\main\\resources\\myKey.keystore");
//        String url="http://192.168.31.136:8004/test/con";
        String url="http://192.168.31.118:8004/test/con";
        url=url+"?context=22222";
        System.out.println(url);
        byte[] bytes1 = URLConnection.postBinResource(url,"", "UTF-8", 30);
        System.out.println("resp:"+new String(bytes1));
    }
}
