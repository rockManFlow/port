package com.rock.port;


import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 创建服务注册中心--服务端
 * Created by caoqingyuan on 2017/7/11.
 */
//@EnableDiscoveryClient
//会扫描根目录下的所有注解
@SpringBootApplication
@Log4j
public class PortApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortApplication.class,args);
        log.info("PortApplication Start");
    }
}
