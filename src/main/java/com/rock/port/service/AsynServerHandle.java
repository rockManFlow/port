//package com.rock.port.service;
//
//import com.rock.comm.base.netty.protos.comm.MessageBase;
//import com.rock.comm.base.netty.server.supports.ServerHandle;
//import org.apache.log4j.Logger;
//
///**
// * 用于具体处理接收过来的消息
// * Created by caoqingyuan on 2017/11/30.
// */
//public class AsynServerHandle implements ServerHandle {
//    private static final Logger logger=Logger.getLogger(AsynServerHandle.class);
//    @Override
//    public MessageBase executor(MessageBase messageBase) throws Throwable {
//        AsynMessageProto messageProto=(AsynMessageProto)messageBase;
//        logger.info("netty server id["+messageProto.getMessageId()+"] data["+messageProto.getContext()+"]");
//
//        //响应
//        AsynMessageProto resp=new AsynMessageProto();
//        resp.setMessageCode("OK");
//        resp.setMessageDesc("已接收");
//        return resp;
//    }
//}
