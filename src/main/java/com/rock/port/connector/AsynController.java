package com.rock.port.connector;

//import com.rock.comm.base.netty.server.RpcServerLoader;
//import com.rock.comm.base.netty.server.supports.ServerHandle;
//import com.rock.port.service.AsynServerHandle;
import org.apache.log4j.Logger;

/**
 * 使用netty进行大批量的数据交互
 * Created by caoqingyuan on 2017/11/30.
 */
public class AsynController {
    private static final Logger logger=Logger.getLogger(AsynController.class);

    public static void startServer() throws InterruptedException {
        logger.info("start netty server");
        String ip="127.0.0.1";
        String port="18003";
//        ServerHandle serverHandle=new AsynServerHandle();
//        RpcServerLoader instance = RpcServerLoader.getInstance();
//        instance.start(ip,port,serverHandle);
    }
}
