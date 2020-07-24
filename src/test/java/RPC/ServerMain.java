package RPC;

import RPC.core.RpcServerLoader;

/**
 * Created by caoqingyuan on 2017/11/1.
 */
public class ServerMain {
    public static void main(String[] args) throws InterruptedException {
        String address="127.0.0.1:18801";
        RpcServerLoader instance = RpcServerLoader.getInstance();
        instance.start(address);
    }
}
