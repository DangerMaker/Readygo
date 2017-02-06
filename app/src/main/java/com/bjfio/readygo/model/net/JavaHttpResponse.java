package com.bjfio.readygo.model.net;

/**
 * Created by codeest on 16/8/28.
 */

public class JavaHttpResponse<T> {

    private int status;
    private String desc;
    T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
