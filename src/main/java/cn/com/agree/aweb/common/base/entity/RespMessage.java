package cn.com.agree.aweb.common.base.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RespMessage {

  @NonNull
  private Boolean success;
  private Integer status;
  private String msg;
  private Object obj;

  private RespMessage() {
  }

  public static RespMessage build() {
    return new RespMessage();
  }

  public static RespMessage build(Boolean success, Integer status, String msg) {
    return new RespMessage().setSuccess(success).setStatus(status).setMsg(msg);
  }

  public static RespMessage build(Boolean success, Integer status, Object obj) {
    return new RespMessage().setSuccess(success).setStatus(status).setObj(obj);
  }

  public static RespMessage build(Boolean success, Integer status, String msg, Object obj) {
    return new RespMessage().setSuccess(success).setStatus(status).setMsg(msg).setObj(obj);
  }

  public static RespMessage ok() {
    return new RespMessage().setSuccess(true).setStatus(200);
  }

  public static RespMessage ok(String msg) {
    return new RespMessage().setSuccess(true).setStatus(200).setMsg(msg);
  }

  public static RespMessage ok(Object obj) {
    return new RespMessage().setSuccess(true).setStatus(200).setObj(obj);
  }

  public static RespMessage ok(String msg, Object obj) {
    return new RespMessage().setSuccess(true).setStatus(200).setMsg(msg).setObj(obj);
  }

  public static RespMessage error(String msg) {
    return new RespMessage().setSuccess(false).setStatus(500).setMsg(msg);
  }

  public static RespMessage error(String msg, Object obj) {
    return new RespMessage().setSuccess(false).setStatus(500).setMsg(msg).setObj(obj);
  }

}
