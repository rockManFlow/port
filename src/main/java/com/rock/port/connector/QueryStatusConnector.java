package com.rock.port.connector;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by caoqingyuan on 2017/10/16.
 */
@RestController
public class QueryStatusConnector {
    private static final Logger logger=Logger.getLogger(QueryStatusConnector.class);

    @RequestMapping("queryStatus/queryTask.html")
    public String queryTask(HttpServletRequest request) throws IOException {
        byte[] bytes = IOUtils.toByteArray(request.getInputStream());
        logger.info("queryTask start...");
        logger.info("req body:"+new String(bytes));
        return "queryTaskOk";
    }
}
