//package com.rock.port.service;
//
//import com.rock.comm.base.netty.client.ClientMessageSendExecutor;
//import com.rock.comm.base.netty.client.supports.ClientSend;
//import com.rock.comm.base.netty.client.supports.ClientSendBase;
//import com.rock.comm.base.netty.protos.comm.MessageBase;
//
///**
// * Created by caoqingyuan on 2017/11/30.
// */
//public class AsynClient {
//    public static void main(String[] args){
//        String ip="127.0.0.1";
//        String port="18003";
//        ClientMessageSendExecutor sendExecutor =null;
//        try {
//            //执行客户端netty请求
//            sendExecutor = new ClientMessageSendExecutor(ip, port);
//            //封装请求体
//            for(int i=0;i<20;i++) {
//                AsynMessageProto request = new AsynMessageProto();
//                request.setContext("client requestBody mmmm "+i);
//                //代理进一步处理请求体
//                ClientSend clientSend = sendExecutor.execute(new ClientSendBase());
//
//
//                MessageBase response = clientSend.send(request);
//                System.out.println("messageId:" + response.getMessageId()+"|respCode:" + response.getMessageCode());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            sendExecutor.stop();
//        }
//        System.out.println("end");
//    }
//}
