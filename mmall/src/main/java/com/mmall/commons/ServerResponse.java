package com.mmall.commons;


import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    /*构造器*/
    public ServerResponse(int status) {
        this.status = status;
    }

    public ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 在json 序列化的时候不出现
     */
    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseEnum.SUCCESS.getCode();
    }

    /*对外开放*/
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg){
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode(),ResponseEnum.ERROR.getMsg());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }

}
