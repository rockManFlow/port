package com.rock.port.controller;

//import com.rock.comm.base.netty.server.RpcServerLoader;
//import com.rock.comm.base.netty.server.supports.ServerHandle;
//import com.rock.port.service.AsynServerHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用netty进行大批量的数据交互
 * Created by caoqingyuan on 2017/11/30.
 */
public class AsynController {
    private static final Logger logger= LoggerFactory.getLogger(AsynController.class);

    public static void startServer() throws InterruptedException {
        logger.info("start netty server");
        String ip="127.0.0.1";
        String port="18003";
//        ServerHandle serverHandle=new AsynServerHandle();
//        RpcServerLoader instance = RpcServerLoader.getInstance();
//        instance.start(ip,port,serverHandle);
    }
}
