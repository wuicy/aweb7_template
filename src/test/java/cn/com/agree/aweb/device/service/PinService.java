package cn.com.agree.aweb.device.service;

import cn.com.agree.aweb.device.bean.Result;
import cn.com.agree.aweb.device.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PinService {

    private final static Logger logger = LoggerFactory.getLogger(PinService.class);

    public final static String PIN = "pin";

    public final static String READONCE = "readonce";

    @Value("${url.management}")
    private String url_management;

    @Value("${url.call_device}")
    private String url_call_device;

    public Result callPin(String ip){
        return HttpUtils.callDevice(ip, url_management, url_call_device,
                PIN, READONCE, 10000, new HashMap());
    }
}
