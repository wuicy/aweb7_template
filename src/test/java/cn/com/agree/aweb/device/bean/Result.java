package cn.com.agree.aweb.device.bean;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
//@ApiModel(value = "Result", description = "请求结果")
public class Result {
//    @ApiModelProperty(value = "返回状态，0-成功；其他-失败")
    @JSONField(name = "status")
    private int status;

//    @ApiModelProperty(value = "返回提示信息")
    @JSONField(name = "msg")
    private String msg;

    @ApiModelProperty(value = "返回结果")
    @JSONField(name = "result")
    private Object result;

    public static final String STATUS = "status";
    public static final String MSG = "error_msg";
    public static final String RESULT = "result";

    //成功标示
    public static final int STATUS_0 = 0;
    public static final String MSG_0 = "success";

    public static Result getSuccessResult(Object object){
        Result result = new Result();
        result.setStatus(STATUS_0);
        result.setResult(object);
        return result;
    }

    public static Result getSuccessResult(Object resultObj, String msgObj){
        Result result = new Result();
        result.setStatus(STATUS_0);
        result.setResult(resultObj);
        result.setMsg(msgObj);
        return result;
    }

    public static Result getErrorsResult(int status, String msg){
        Result result = new Result();
        result.setStatus(status);
        result.setMsg(msg);
        return result;
    }
}

