package com.rock.port.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by caoqingyuan on 2017/10/16.
 */
@RestController
@Slf4j
public class QueryStatusConnector {

    @RequestMapping("queryStatus/queryTask.html")
    public String queryTask(HttpServletRequest request) throws IOException {
        byte[] bytes = IOUtils.toByteArray(request.getInputStream());
        log.info("queryTask start...");
        log.info("req body:"+new String(bytes));
        return "queryTaskOk";
    }
}
