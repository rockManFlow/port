package com.rock.port.service;

import com.rock.port.util.RSAUtil;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 验证请求是否合法
 * Created by caoqingyuan on 2017/11/3.
 */
public class SignSibmitService {
    public static boolean sign(String context,String sign,String privateUrl){
        try {
            FileInputStream read=new FileInputStream(privateUrl);
            String privateKey=new String(IOUtils.toByteArray(read));
            String st=RSAUtil.decrypt(sign,privateKey);
            if(st.equals(context)){
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
