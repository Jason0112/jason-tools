package com.tools.common.advice;

/**
 * date: 2017/5/22
 * description :返回结果
 *
 * @author : zhencai.cheng
 */
public class ResponseResult {
    private String traceID;
    private int code = 200;
    private String msg = "正常调用";
    private Object data = new Object();

    public ResponseResult() {
    }

    public ResponseResult(String traceID, int code, String msg) {
        this.traceID = traceID;
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(String traceID, int code, String msg, Object data) {
        this.traceID = traceID;
        this.code = code;
        this.msg = msg;
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
