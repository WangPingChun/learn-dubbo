package com.imooc.common.utils;

/**
 * 自定义响应数据结构.
 * @author : chris
 * 2018-06-30
 */
public class ImoocJsonResult {
    /**
     * 响应业务状态
     * 200 成功
     * 500 错误,错误信息在msg中
     * 501 bean验证错误,不关多少个错误都以map形式返回
     * 502 拦截器拦截到用户token出错
     * 555 异常抛出信息
     */
    private Integer status;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    /**
     * 不使用
     */
    private String ok;

    public ImoocJsonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ImoocJsonResult(Object data) {
        this.status = 200;
        this.msg = "ok";
        this.data = data;
    }

    public static ImoocJsonResult build(Integer status, String msg, Object data) {
        return new ImoocJsonResult(status, msg, data);
    }

    public static ImoocJsonResult ok(Object data) {
        return new ImoocJsonResult(data);
    }

    public static ImoocJsonResult ok() {
        return new ImoocJsonResult(null);
    }

    public static ImoocJsonResult errorMsg(String msg) {
        return new ImoocJsonResult(500, msg, null);
    }

    public static ImoocJsonResult errorMap(Object data) {
        return new ImoocJsonResult(501, "error", data);
    }

    public static ImoocJsonResult errorTokenMsg(String msg) {
        return new ImoocJsonResult(502, msg, null);
    }

    public static ImoocJsonResult errorException(String msg) {
        return new ImoocJsonResult(555, msg, null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
