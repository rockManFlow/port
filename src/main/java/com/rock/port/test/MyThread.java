package com.rock.port.test;

/**
 * Created by caoqingyuan on 2017/10/27.
 */
public class MyThread extends Thread {
    private String threadId;
    private Thread t;
    public MyThread(String threadId,Thread t){
        this.threadId=threadId;
        this.t=t;
    }
    public void run(){
        if(threadId.equals("11")){
            while (true){
                try{
                    System.out.println("run id:"+threadId);
                    Thread.sleep(1*1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            if(t!=null){
                Thread a=new MyThread("11",null);
                a.setDaemon(true);
                a.start();
                try{
                    System.out.println("run id:"+threadId);
                    Thread.sleep(1*1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return;
            }
            try{
                System.out.println("run id:"+threadId);
                Thread.sleep(10*1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread b=new MyThread("220",new Thread());
        b.start();
        Thread bb=new MyThread("221",null);
        bb.start();
//        System.out.println("sss");
//        Thread.sleep(6*1000);
        System.out.println("end");
    }
}
