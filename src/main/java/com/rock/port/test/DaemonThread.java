package com.rock.port.test;

/**
 * Created by caoqingyuan on 2017/11/16.
 */
public class DaemonThread implements Runnable{
    private String name;
    public DaemonThread(String name){
        this.name=name;
    }
    @Override
    public void run() {
        System.out.println("start");
        System.out.println(name);
        if(name.equals("2222")){
            try {
                Thread.sleep(6*1000);
                System.out.println("sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }

    public static void main(String[] args){
        Thread threada = new Thread(new DaemonThread("1111"));
        Thread threadb = new Thread(new DaemonThread("2222"));
        threadb.setDaemon(true);
        threadb.start();
        threada.start();
        System.out.println("main");
    }
}
