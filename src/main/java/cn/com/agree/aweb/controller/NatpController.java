package cn.com.agree.aweb.controller;

import cn.com.agree.afa.natpClient.NatpClient;
import cn.com.agree.afa.natpClient.NatpPackage;
import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.entity.dto.NatpDto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/natp")
@Api("natp")
public class NatpController {

    @Value("${natp.host}")
    String host;
    @Value("${natp.port}")
    String port;
    @Value("${natp.timeout}")
    String timeOut;

    final static String version = "version:3";
    final static String isRespnse = "true";

    @PostConstruct
    public void init() {
        if(timeOut == null || Integer.parseInt(timeOut) < 0) {
            timeOut = "10";
        }
    }

    @PostMapping("/natpTest")
    @ApiOperation(value = "natp调用")
    public RespMessage natpTest(@RequestBody NatpDto natpDto) throws Exception {

        JSONObject jsonData = JSON.parseObject(JSONObject.toJSONString(natpDto));

        //调用交易的参数
        String[] args = new String[8];
        //版本号
        args[0] = version;
        //服务ip
        args[1] = host;
        //端口
        args[2] = port;
        String mc = jsonData.getString("mc");
        if(StringUtils.isEmpty(mc)) {
            return RespMessage.error("mc不能为空");
        }
        //mc码
        args[3] = mc;
        String tc = jsonData.getString("tc");
        if(StringUtils.isEmpty(tc)) {
            return RespMessage.error("tc不能为空");
        }
        //tc码
        args[4] = tc;
        //交易数据，需要将数据拼装成key:value,key:value的字符串
        args[5] = "";
        //是否有响应结果
        args[6] = isRespnse;
        //连接时间
        args[7] = timeOut;

        //取得的值在NatpPackage中的keyList与valueList中
        NatpPackage natpPackage = NatpClient.exchange(args);

        return RespMessage.ok();
    }
}
