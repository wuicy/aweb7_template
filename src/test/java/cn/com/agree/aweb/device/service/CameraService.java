package cn.com.agree.aweb.device.service;

import cn.com.agree.aweb.device.bean.Result;
import cn.com.agree.aweb.device.utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;

@Service
public class CameraService {

    private final static Logger logger = LoggerFactory.getLogger(CameraService.class);

    public final static String CAMERA = "camera";

    public final static String OPEN = "open";
    public final static String FETCH = "fetch";
    public final static String CLOSE = "close";

    @Value("${url.management}")
    private String url_management;

    @Value("${url.call_device}")
    private String url_call_device;

    @Value("${device.save_pdf_path}")
    private String device_save_pdf_path;

    public Result callCamera(String ip, String method){
        Result result =  HttpUtils.callDevice(ip, url_management, url_call_device,
                CAMERA, method, 10000, new HashMap());
        if(method.equalsIgnoreCase(FETCH)){
            saveFile(result); //解析并保存（注意保存路径配置为本机存在路径）
        }
        return result;
    }

    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();
    public void saveFile(Result result){
        try {
            String filePath = device_save_pdf_path + File.separator + "tmp_camera_picture.png";
            File file = new File(filePath);
            try (OutputStream fileOutput = new FileOutputStream(file)) {
                JSONObject jsonObject = (JSONObject)result.getResult();
                String value = (String)jsonObject.get("output");
                byte[] value_byte  = decoder.decode(value.getBytes("utf-8"));
                fileOutput.write(value_byte);
            } catch (IOException e) {
                logger.error("读取文件出错：{}", e.getMessage());
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e.getStackTrace());
        }
    }
}
