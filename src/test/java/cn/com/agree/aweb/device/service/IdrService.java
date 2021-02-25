package cn.com.agree.aweb.device.service;

import cn.com.agree.aweb.device.bean.Result;
import cn.com.agree.aweb.device.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IdrService {
    private final static Logger logger = LoggerFactory.getLogger(IdrService.class);

    private final static String IDR = "idr";

    private final static String READ_IDR_ALL= "readidrall";

    @Value("${url.management}")
    private String url_management;

    @Value("${url.call_device}")
    private String url_call_device;

    public Result callIdr(String ip){
        return HttpUtils.callDevice(ip, url_management, url_call_device,
                IDR, READ_IDR_ALL, 15000, new HashMap());
    }
}
