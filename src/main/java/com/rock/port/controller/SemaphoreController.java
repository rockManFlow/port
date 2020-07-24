package com.rock.port.controller;

import com.rock.port.util.URLConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Semaphore;

/**
 * TODO 测试Semaphore信号量，可以控制接口或者指定的资源可以同时被几个线程访问
 * Created by caoqingyuan on 2017/12/11.
 */
@RestController
@Slf4j
public class SemaphoreController {
    private Semaphore available = new Semaphore(1, true);

    @RequestMapping("/testA.htm")
    private String testA(String context) {
        log.info("testA entry");
        try {
            //来获取一个许可
            available.acquire();
            log.info("执行相应的任务，并睡5s 当前可用许可数："+available.availablePermits());
            Thread.sleep(5 * 1000);
            log.info("释放信号许可，返还给信号量");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            available.release();
        }
        return "TestA OK";
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] bytes = URLConnection.postBinResource("http://localhost:8084/testA", "hhhh", "UTF-8", 30);
                        System.out.println("resp:" + new String(bytes));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println("end");
    }
}
