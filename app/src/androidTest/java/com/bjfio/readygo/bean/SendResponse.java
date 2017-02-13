package com.bjfio.readygo.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 19082 on 2016/5/20.
 */
public class SendResponse {

    private int code;
    private String msg;
    private String msgId;
    private int type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static SendResponse parseResponse(String json){
        try {
            JSONObject object=new JSONObject(json);
            SendResponse response=new SendResponse();
            response.setMsgId(object.optString("msgid"));
            response.setMsg(object.optString("msg"));
            response.setCode(object.optInt("code"));
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
