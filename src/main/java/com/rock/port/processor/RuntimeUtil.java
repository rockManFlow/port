package com.rock.port.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * 在本机执行脚本命令
 * Created by caoqingyuan on 2018/1/23.
 */
public class RuntimeUtil {
    private static final Logger logger= LoggerFactory.getLogger(RuntimeUtil.class);
    public static String processId;
    public static String exec(String command) {
        StringBuilder sb = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            process.getOutputStream().close();
            reader.close();
            process.destroy();
        } catch (Exception e) {
            logger.error("执行外部命令错误，命令行:" + command, e);
        }
        return sb.toString();
    }

    public static String jps() {
        return exec("jps -l");
    }

    //显示当前进程id
    public static void showCurrentProcessId(){
        //返回 Java 虚拟机的运行时系统的管理 Bean
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        //获取当前的进程id
        Integer id=Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();
        logger.info("PROCESS ID["+id+"]");
        processId=""+id;
    }

    public static void main(String[] args){
        showCurrentProcessId();
    }
}
