package RPC.model;

import java.io.Serializable;

/**
 * 自定义netty请求体--协议
 * Created by caoqingyuan on 2017/10/30.
 */
public class MyMessageRequest implements Serializable {
    private String messageId;
    private String type;
    private String desc;
    private String context;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
