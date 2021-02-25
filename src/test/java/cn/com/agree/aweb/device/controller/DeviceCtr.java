package cn.com.agree.aweb.device.controller;

import cn.com.agree.aweb.device.bean.ExcelData;
import cn.com.agree.aweb.device.bean.Result;
import cn.com.agree.aweb.device.service.*;
import cn.com.agree.aweb.device.utils.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(description = "外设调用")
@RestController
@RequestMapping("/device")
public class DeviceCtr {

    private final static Logger logger = LoggerFactory.getLogger(DeviceCtr.class);

    @Autowired
    private FicService ficService;
    @Autowired
    private PinService pinService;
    @Autowired
    private IdrService idrService;
    @Autowired
    private PrinterService printerService;
    @Autowired
    private PaperScannerService paperScannerService;
    @Autowired
    private CameraService cameraService;

    //测试
    @ApiOperation(value = "测试接口", notes = "测试服务是否启动")
    @PostMapping("/test")
    public Result test (HttpServletRequest request){
        logger.info("请求了/device/test");
        String ip = HttpUtils.getIPAddress(request);
        Result result = Result.getSuccessResult("ip:" + ip);
        result.setMsg("success");
        return result;
    }

    //密码键盘
    @ApiOperation(value = "调用密码键盘")
    @PostMapping("/pin")
    public Result callPin(HttpServletRequest request){
        String ip = HttpUtils.getIPAddress(request);
        logger.info("调用密码键盘: /device/pin; ip = {}", ip);
        if("undefine".equals(ip)){
            logger.debug("ip 不存在");
            return Result.getErrorsResult(1, "缺少参数：ip");
        }
        Result result = pinService.callPin(ip);
        logger.info("调用密码键盘返回结果：{}", result);
        return result;
    }

    //金融ic卡
    @ApiOperation(value = "调用金融ic卡")
    @PostMapping("/fic")
    public Result callFic(HttpServletRequest request){
        String ip = HttpUtils.getIPAddress(request);
        logger.info("调用金融ic卡: /device/fic; ip = {}", ip);
        if("undefine".equals(ip)){
            logger.debug("ip 不存在");
            return Result.getErrorsResult(1, "缺少参数：ip");
        }
        Result result = ficService.callFic(ip);
        logger.info("调用金融ic卡返回结果：{}", result);
        return result;
    }

    //身份证
    @ApiOperation(value = "调用身份证")
    @PostMapping("/idr")
    public Result callIdr(HttpServletRequest request){
        String ip = HttpUtils.getIPAddress(request);
        logger.info("调用身份证: /device/idr; ip = {}", ip);
        if("undefine".equals(ip)){
            logger.debug("ip 不存在");
            return Result.getErrorsResult(1, "缺少参数：ip");
        }
        Result result = idrService.callIdr(ip);
        logger.info("调用金融ic卡返回结果：{}", result);
        return result;
    }

    //打印机
    @ApiOperation(value = "调用打印机")
    @PostMapping("/printer")
    public Result callPrinter(@RequestBody List<ExcelData> data, HttpServletRequest request){
        String ip = HttpUtils.getIPAddress(request);
        logger.info("调用打印机: /device/printer; ip = {}, data = {}", ip, data);
        if("undefine".equals(ip)){
            logger.debug("ip 不存在");
            return Result.getErrorsResult(1, "缺少参数：ip");
        }
//        List<ExcelData> list = new ArrayList<>();
//        ExcelData data = new ExcelData();
//        data.setId(1);
//        data.setNumber(1);
//        data.setName("test1");
//        data.setDate(new Date());
//        list.add(data);
        Result result = printerService.callPrinter(ip, data);
        logger.info("调用打印机返回结果：{}", result);
        return result;
    }

    //下载打印机文件
    @ApiOperation(value = "下载打印机文件", notes = "入参：filePath(文件在服务器下载区域的路径)")
    @GetMapping("/getPrintFile")
    public void getPrintFile(@RequestParam("filePath") String filename, HttpServletResponse response){
        printerService.getPrintFile(filename, response);
    }

    //扫描仪
    @ApiOperation(value = "调用扫描仪", notes = "入参：doubleScan（是否双面打印），返回output中数据格式{'1':'第二页base64数据', '2':'第一页base64数据'}")
    @PostMapping("/paperScanner")
    public Result callPaperScanner(@RequestParam("doubleScan") boolean doubleScan, HttpServletRequest request){
        String ip = HttpUtils.getIPAddress(request);
        logger.info("调用扫描仪: /device/paperScanner; ip = {}", ip);
        if("undefine".equals(ip)){
            logger.debug("ip 不存在");
            return Result.getErrorsResult(1, "缺少参数：ip");
        }
        Result result = paperScannerService.callPaperScannner(ip, doubleScan);
        logger.info("调用扫描仪返回结果：{}", result);
        return result;
    }


    @ApiOperation(value = "打开摄像头")
    @PostMapping("/camera/open")
    public Result callCameraOpen(HttpServletRequest request){
        String ip = HttpUtils.getIPAddress(request);
        logger.info("打开摄像头: /device/camera/open; ip = {}", ip);
        if("undefine".equals(ip)){
            logger.debug("ip 不存在");
            return Result.getErrorsResult(1, "缺少参数：ip");
        }
        Result result = cameraService.callCamera(ip, CameraService.OPEN);
        logger.info("打开摄像头返回结果：{}", result);
        return result;
    }

    @ApiOperation(value = "拍照")
    @PostMapping("/camera/fetch")
    public Result callCameraFetch(HttpServletRequest request){
        String ip = HttpUtils.getIPAddress(request);
        logger.info("拍照: /device/camera/fetch; ip = {}", ip);
        if("undefine".equals(ip)){
            logger.debug("ip 不存在");
            return Result.getErrorsResult(1, "缺少参数：ip");
        }
        Result result = cameraService.callCamera(ip, CameraService.FETCH);
        logger.info("拍照返回结果：{}", result);
        return result;
    }

    @ApiOperation(value = "关闭摄像头")
    @PostMapping("/camera/close")
    public Result callCameraclose(HttpServletRequest request){
        String ip = HttpUtils.getIPAddress(request);
        logger.info("关闭摄像头: /device/camera/close; ip = {}", ip);
        if("undefine".equals(ip)){
            logger.debug("ip 不存在");
            return Result.getErrorsResult(1, "缺少参数：ip");
        }
        Result result = cameraService.callCamera(ip, CameraService.CLOSE);
        logger.info("关闭摄像头返回结果：{}", result);
        return result;
    }

//    //-------------------------承德--------------------------------------------
//    @ApiOperation(value = "打开摄像头")
//    @PostMapping("/camera/open")
//    public Result callCameraOpen(HttpServletRequest request){
//        String ip = HttpUtils.getIPAddress(request);
//        logger.info("打开摄像头: /device/camera/open; ip = {}", ip);
//        if("undefine".equals(ip)){
//            logger.debug("ip 不存在");
//            return Result.getErrorsResult(1, "缺少参数：ip");
//        }
//        // 注意：下面的mac：是运行交易的对应客户端上电脑的mac地址
//        String mac = "AC220B07744E";
//        Result result = CameraService2.callDevice(CameraService.OPEN, mac);
//        logger.info("打开摄像头返回结果：{}", result);
//        return result;
//    }
//
//    @ApiOperation(value = "拍照")
//    @PostMapping("/camera/fetch")
//    public Result callCameraFetch(HttpServletRequest request){
//        String ip = HttpUtils.getIPAddress(request);
//        logger.info("拍照: /device/camera/fetch; ip = {}", ip);
//        if("undefine".equals(ip)){
//            logger.debug("ip 不存在");
//            return Result.getErrorsResult(1, "缺少参数：ip");
//        }
//        // 注意：下面的mac：是运行交易的对应客户端上电脑的mac地址
//        String mac = "AC220B07744E";
//        Result result = CameraService2.callDevice(CameraService.FETCH, mac);
//        logger.info("拍照返回结果：{}", result);
//        return result;
//    }
//
//    @ApiOperation(value = "关闭摄像头")
//    @PostMapping("/camera/close")
//    public Result callCameraclose(HttpServletRequest request){
//        String ip = HttpUtils.getIPAddress(request);
//        logger.info("关闭摄像头: /device/camera/close; ip = {}", ip);
//        if("undefine".equals(ip)){
//            logger.debug("ip 不存在");
//            return Result.getErrorsResult(1, "缺少参数：ip");
//        }
//        // 注意：下面的mac：是运行交易的对应客户端上电脑的mac地址
//        String mac = "AC220B07744E";
//        Result result = CameraService2.callDevice(CameraService.CLOSE, mac);
//        logger.info("关闭摄像头返回结果：{}", result);
//        return result;
//    }
}
