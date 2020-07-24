package com.rock.port.connector;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caoqingyuan on 2017/10/23.
 */
@RestController
//@RequestMapping("netty")
public class TestController {
    private static final Logger logger=Logger.getLogger(TestController.class);

    @RequestMapping("/test.do")
    @ResponseBody
    public String test(String context){
        logger.info("context:"+context);
        return "OK";
    }

    @RequestMapping("testB.htm")
    private String testB(String context){
        logger.info("vm testB entry");
        logger.info("context:"+context);
        return "vm testB OK";
    }

    @RequestMapping("testD.htm")
    private String testD(){
        logger.info("vm zhi is testD");
        logger.info("oooooooo");
        return "OK+00000000";
    }
}
