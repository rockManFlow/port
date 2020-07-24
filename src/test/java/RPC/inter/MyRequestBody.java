package RPC.inter;

/**
 * Created by caoqingyuan on 2017/10/30.
 */
public interface MyRequestBody {
    //使用代理来进行统一的发送不同的消息体
    public Object createAndSendRequestBody();
}
