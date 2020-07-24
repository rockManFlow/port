package com.rock.port.processor;

import com.rock.port.util.OSType;
import com.rock.port.util.OSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于实现服务的重启
 * Created by caoqingyuan on 2018/1/23.
 */
public class ServerManager {
    private static final Logger logger= LoggerFactory.getLogger(ServerManager.class);
    public void restart(){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    logger.info("开始停止服务");
                    String stop="kill -9 "+RuntimeUtil.processId;
                    RuntimeUtil.exec(stop);
                    logger.info("停止服务完成！");
                    Thread.sleep(10000);//等10秒
                    logger.debug("开始启动服务");
                    String restartCmd = "ant -buildfile ../build.xml Run";
                    RuntimeUtil.exec(restartCmd);
                    logger.debug("服务启动完成！");
                } catch (Exception e) {
                    logger.error("重启失败，原因：", e);
                }
            }
        });
        logger.debug("程序准备重启！");
        System.exit(0);
    }


    //代码获取当前程序的实际路径
    public static String getJarExecPath(Class clazz) {
        String path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (OSUtil.getOSname().equals(OSType.Windows)) {
            return path.substring(1);
        }
        return path;
    }
}
