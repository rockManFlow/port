package RPC.model;

import java.io.Serializable;

/**
 * Created by caoqingyuan on 2017/10/30.
 */
public class MyMessageResponse implements Serializable {
    private String messageId;
    private String repsCode;
    private String desc;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRepsCode() {
        return repsCode;
    }

    public void setRepsCode(String repsCode) {
        this.repsCode = repsCode;
    }

    public String toString(){
        return "repsCode:"+this.repsCode+"|desc:"+this.desc+"|messageId:"+messageId;
    }
}
