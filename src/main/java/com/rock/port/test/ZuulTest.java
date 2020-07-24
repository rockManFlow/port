package com.rock.port.test;


import com.rock.port.util.URLConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caoqingyuan on 2017/10/18.
 */
public class ZuulTest implements Runnable {
    private int i;
    public ZuulTest(int i){
        this.i=i;
    }
    @Override
    public void run() {
        String url = "http://192.168.31.136:7773/port_gateway/queryStatus/queryTask.html";
        Map map = new HashMap();
        map.put("data", i+"TESTGGGG");
        try {
            byte[] bytes1 = URLConnection.postBinResource(url, map, "UTF-8", 30);
            System.out.println("resp:" + new String(bytes1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
