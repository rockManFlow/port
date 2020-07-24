package com.rock.port.controller;

import com.rock.port.service.Constants;
import com.rock.port.service.SignSibmitService;
import com.rock.port.service.ThreadPoolService;
import com.rock.port.util.JsonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by caoqingyuan on 2017/10/18.
 */
@RestController
public class SubmitTaskController {
    private static final Logger logger=Logger.getLogger(SubmitTaskController.class);

    @RequestMapping("submit/task.htm")
    public String work(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        JSONObject jsonObject = JsonUtil.mapOrBeanToJSON(new String(bytes));
        return dealwithSubmit(jsonObject);
    }

    private String dealwithSubmit(JSONObject jsonObject){
        String idNo=(String)jsonObject.get("idNo");
        String cardNo=(String)jsonObject.get("cardNo");
        String taskType=(String)jsonObject.get("taskType");
        Integer amount=(Integer)jsonObject.get("amount");
        String sign=(String)jsonObject.get("sign");
        String fileUrl="D:\\myProjects\\port\\src\\main\\resources\\port_submit_merket.private";

        boolean b=SignSibmitService.sign("amount="+amount+"&cardNo="+cardNo+"&idNo="+idNo+"&taskType="+taskType,sign,fileUrl);
        if(!b){
            return "valide fail";
        }
        String requestBody= Base64Utils.encodeToString(jsonObject.toString().getBytes());

        Executor pool = ThreadPoolService.getPoolExecutor(Constants.SUBMIT_CONNECTOR_POOL_NAME);
//        pool.execute(new SubmitService(taskEntity));

        return "OK";
    }
}
