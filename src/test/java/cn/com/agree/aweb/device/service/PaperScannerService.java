package cn.com.agree.aweb.device.service;

import cn.com.agree.aweb.device.bean.Result;
import cn.com.agree.aweb.device.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperScannerService {

    private final static Logger logger = LoggerFactory.getLogger(PaperScannerService.class);

    private final static String PAPER_SCANNER = "paperScanner";
    private final static String GET_DEVICES= "getdevices";
    private final static String SCAN= "scan";

    @Value("${url.management}")
    private String url_management;

    @Value("${url.call_device}")
    private String url_call_device;

    public Result callPaperScannner(String ip, boolean isDoubleScan){
        //获取指定ip上可以进行调用的扫描仪列表
        Result getDevicesRes =  HttpUtils.callDevice(ip, url_management, url_call_device,
                PAPER_SCANNER, GET_DEVICES, 25000, new HashMap());
        logger.debug("getdevices 返回结果：{}", getDevicesRes);
        if(getDevicesRes.getStatus() != 0){
            return getDevicesRes;
        }
        //这里（默认）使用第一个
        Map mapResult = (Map) getDevicesRes.getResult();
        List scannerList = (List)mapResult.get("output");
        if(scannerList.size() == 0){
            return Result.getErrorsResult(-1,"没有可以调用的扫描仪");
        }
        String scanner = (String) scannerList.get(0);
        HashMap bodyMap = new HashMap();
        bodyMap.put("name", scanner);
        if(isDoubleScan){
            bodyMap.put("doubleScan", "true");
        }else {
            bodyMap.put("doubleScan", "false");
        }
        Result scanResult =  HttpUtils.callDevice(ip, url_management, url_call_device,
                PAPER_SCANNER, SCAN, 40000, bodyMap);
        ////测试：解码并保存到本地
//        String file = "G:\\0000_01_code_project\\02_AWEB_Spring\\test\\test2.jpg";
//        try (OutputStream outputStream = new FileOutputStream(new File(file))) {
//            String output = (String)((Map)scanResult.getResult()).get("output");
//            Map jsonObject = (Map)JSON.parse(output);
//            String obj1 = (String)jsonObject.get("1");
//            byte[] b = Base64.decodeBase64(obj1);
//            outputStream.write(b);
//        }catch (Exception e){
//            logger.error(e.getMessage(), e.getStackTrace());
//        }
        return scanResult;
    }
}
