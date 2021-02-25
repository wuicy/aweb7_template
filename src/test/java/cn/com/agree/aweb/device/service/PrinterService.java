package cn.com.agree.aweb.device.service;

import cn.com.agree.aweb.device.bean.Result;
import cn.com.agree.aweb.device.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;

@Service
public class PrinterService {

    private final Logger logger = LoggerFactory.getLogger(PrinterService.class);

    public final static String PRINTER = "printer";

    public final static String METHOD_PRINT = "print";

    @Value("${url.management}")
    private String url_management;

    @Value("${url.call_device}")
    private String url_call_device;

    @Value("${url.aweb_api}")
    private String url_aweb_api;

    @Value("${device.save_pdf_path}")
    private String device_save_pdf_path;

    @Value("${device.save_pdf_name}")
    private String device_save_pdf_name;

    public Result callPrinter(String ip, List data){
        logger.info("请求的数据 ip = {}, list ={}", ip, data);
        //如果文件存在，删除，重新创建
        String filePath =  device_save_pdf_path + File.separator + device_save_pdf_name;
        File pdf = new File(filePath);
        pdf.deleteOnExit();
        try {
            pdf.createNewFile();
        }catch (IOException e){
            logger.error("创建pdf文件异常：{}", e.getMessage());
            return Result.getErrorsResult(-1, e.getMessage());
        }

        //发送请求，获取pdf文件
        String url = url_aweb_api + "/excel/export/pdf";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity httpEntity  = new HttpEntity<List>(data, headers);
        byte[] result = null;
        try{
            ResponseEntity<byte[]> response  = restTemplate.exchange(url, HttpMethod.POST, httpEntity, byte[].class);
            result = response.getBody();
        }catch (RestClientException e){
            logger.error("数据转pdf出现异常：{}", e.getMessage());
            return Result.getErrorsResult(-1, e.getMessage());
        }

        //保存pdf文件
        try(InputStream inputStream = new ByteArrayInputStream(result);
            OutputStream outputStream = new FileOutputStream(new File(filePath));){
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        }catch (Exception e){
            logger.error(e.getMessage(), e.getStackTrace());
            return Result.getErrorsResult(-1, e.getMessage());
        }

        //发送给外设云进行调用打印机
        HashMap bodyMap = new HashMap();
        bodyMap.put("filename", device_save_pdf_name);
        return HttpUtils.callDevice(ip, url_management, url_call_device,
                PRINTER, METHOD_PRINT, 10000, bodyMap);
    }

    public void getPrintFile(String filename, HttpServletResponse response){
        logger.info("下载的文件名：{}", filename);
        try {
            OutputStream out = response.getOutputStream();
            String filePath = device_save_pdf_path + File.separator + filename;
            File file = new File(filePath);
            if (!file.exists()) {
                logger.error("下载的文件不存在");
                return;
            } else {
                try (InputStream fileInput = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int n = 0;
                    while (-1 != (n = fileInput.read(buffer)))
                        out.write(buffer, 0, n);
                } catch (IOException e) {
                    logger.error("读取文件出错：{}", e.getMessage());
                }
            }
        }catch (IOException e){
            logger.error(e.getMessage(), e.getStackTrace());
        }
    }
}
