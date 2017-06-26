package com.tools.wechat.bean;

import com.tools.wechat.util.TraceIDThreadLocal;
import org.springframework.http.HttpStatus;

/**
 * date: 2017/6/11
 * description :
 *
 * @author : zhencai.cheng
 */
public class ResponseResult {
    private String traceID;
    private int code = HttpStatus.OK.value();
    private String msg = "正常调用";
    private Object data = new Object();

    public ResponseResult() {
    }

    public ResponseResult(String msg) {
        this(-100, msg);
    }

    public ResponseResult(int code, String msg) {
        this.traceID = TraceIDThreadLocal.getTraceID();
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(int code, String msg, Object data) {
        this(code, msg);
        if (data != null) {
            this.data = data;
        } else {
            this.data = new Object();
        }

    }

    public String getTraceID() {
        return this.traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
