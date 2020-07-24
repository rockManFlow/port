package com.rock.port.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by caoqingyuan on 2017/10/18.
 */
public class JsonUtil {
    //array或list转json
    public static JSONArray arrayOrListToJSON(Object ob){
        JSONArray jsonArray = JSONArray.fromObject(ob);
        return jsonArray;
    }

    //map  bean转json
    public static JSONObject mapOrBeanToJSON(Object ob){
        JSONObject jsonObject = JSONObject.fromObject(ob);
        return jsonObject;
    }
}
