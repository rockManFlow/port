package com.rock.port.controller;

import com.rock.port.modle.Type;
import com.rock.port.util.HostUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * TODO 反向代理接口---用于转发请求
 * Created by caoqingyuan on 2018/1/23.
 */
@RestController
public class ProxyController {
    private static final Logger logger = Logger.getLogger(ProxyController.class);
    private static final Map<String, Type> ipList = new HashMap<>();//白名单列表
    private static final Map<String, String> sysMapper = new HashMap<>();//系统映射
    @PostConstruct
    public void init() {
        ipList.put("127.0.0.1", Type.ALLOW);
        ipList.put("192.168.0.31", Type.DEYN);
        ipList.put("0:0:0:0:0:0:0:1", Type.ALLOW);
        try {
            Properties properties = null;
            Set<String> keySet = properties.stringPropertyNames();
            for(String key:keySet){
                sysMapper.put(key,(String)properties.get(key));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/{serverCode}/action.do")
    public void action(HttpServletRequest request, @PathVariable("serverCode") String serverCode, HttpServletResponse response) throws IOException {
        if (!whiteListCheck(request)) {
            return;
        }

        String reqBody;
        String method = request.getMethod();
        if("GET".equals(method)){
            reqBody = new String(request.getQueryString());
        }else{
            reqBody = new String(IOUtils.toByteArray(request.getInputStream()));
        }

        String respBody="默认值";
        try {
            //仅能用于本系统内的路径映射
            String respUrl = routeChoose(serverCode);
            //仅适用于相同系统的请求转发
//            request.getRequestDispatcher(respUrl).forward(request, response);
//            byte[] bytes1 = URLConnection.postBinResource(respUrl, reqBody, "UTF-8", 30);
            respBody=new String("null");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(respBody.getBytes());
        outputStream.flush();
        outputStream.close();
        System.out.println("end");
    }

    private Boolean whiteListCheck(HttpServletRequest request) {
        String ipAdress = HostUtil.getIpAdress(request);
        logger.info("request ip:" + ipAdress);
        Type type = ipList.get(ipAdress);
        if (type == null || Type.DEYN == type) {
            return false;
        }
        logger.info(type);
        return true;
    }

    private String routeChoose(String serverCode) {
        if (serverCode == null || "".equals(serverCode)) {
        }
        String url = sysMapper.get(serverCode);
        if (url == null || "".equals(url)) {
        }
        return url;
    }
}
