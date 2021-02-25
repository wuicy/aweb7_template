package cn.com.agree.aweb.entity.dto;

import lombok.Data;

import java.util.HashMap;

/**
 * @desc: natp请求体
 */
@Data
public class NatpDto {

    private String mc;
    private String tc;
    private HashMap<String,Object> data;
}
