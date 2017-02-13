package com.bjfio.readygo.bean;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 19082 on 2016/5/19.
 */
public class Msg {

    public class State{
        public static final int SENDING=0;
        public static final int SEND_FAILED=1;
        public static final int SENDED=2;
    }
    private String msgId;
    private String chatId;
    private String loginCid;
    private String creatorUid;
    private String participatorUids;
    /*
    *  0 接收的
    *  1 发送的
    * */
    private int msgType;
    private MsgContent content;
    private MsgFile file;
    private long time;

    private boolean bShowTime;

    public void setBShowTime(boolean bShowTime) {
        this.bShowTime=bShowTime;
    }

    public boolean isShowTime(){
        return bShowTime;
    }

    public String getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public MsgFile getFile() {
        return file;
    }

    public void setFile(MsgFile file) {
        this.file = file;
    }

    /*
        * 0，未发送
        * 1，发送失败
        * 2，已发送
        * */
    private int state;

    public String getParticipatorUids() {
        return participatorUids;
    }

    public void setParticipatorUids(String participatorUids) {
        this.participatorUids = participatorUids;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }


    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getLoginCid() {
        return loginCid;
    }

    public void setLoginCid(String loginCid) {
        this.loginCid = loginCid;
    }

    public MsgContent getContent() {
        return content;
    }

    public void setContent(MsgContent content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public static Msg parseMsgReceived(String json,String myCid){
        try {
            JSONObject object=new JSONObject(json);
            Msg msg=new Msg();
            String chatId=object.optString("chatid");
            String timeStr=object.optString("ltime");
            msg.setChatId(chatId);
            msg.setLoginCid(myCid);
            msg.setState(2);
            if (!TextUtils.isEmpty(timeStr)&&!"[]".equals(timeStr)){
                double d=Double.parseDouble(timeStr);
                long l= Math.round(d);
                msg.setTime(l);
            }
//            msg.setTime(TimeFormat.getTimeStampFromStr(timeStr));
            msg.setMsgId(object.optString("msgid"));
            msg.setMsgType(0);
            msg.setContent(MsgContent.parseJsonFromServer(object.optString("msgText")));
            msg.setFile(MsgFile.parseFile(object.optString("msgFile")));
            msg.setCreatorUid(object.optString("sendUid"));
            JSONArray array= object.optJSONArray("participators");
            StringBuffer sb=new StringBuffer();
            if (array!=null){
                for(int i=0;i<array.length();i++){
                    String value=array.getString(i);
                    sb.append(value+",");
                }
            }
            msg.setParticipatorUids(String.valueOf(sb));
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送一条消息的
     * @param json
     * @param myCid
     * @return
     */
    public static Msg parseMsgReceivedFromCallback(String json,String myCid){
        try {
            JSONObject object=new JSONObject(json);
            Msg msg=new Msg();
            String timeStr=object.optString("field_ezc_ltime");
            JSONObject root=new JSONObject(timeStr);
            JSONArray array0=root.getJSONArray("und");
            String time=null;
            if (array0!=null&&array0.length()>0){
                JSONObject object1=array0.getJSONObject(0);
                time=object1.optString("value");
            }
            if (!TextUtils.isEmpty(time)){
                msg.setTime(Long.parseLong(time));
            }
            msg.setMsgId(object.optString("nid"));
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Msg> parseMsgList(String json,String myUid){
        List<Msg> list=new ArrayList<>();
        if (!TextUtils.isEmpty(json)){
            try {
                JSONArray array=new JSONArray(json);
                if (array!=null){
                    for(int i=0;i<array.length();i++){
                        String object=array.optString(i);
                        Msg msg=parseMsgReceived(object,myUid);
                        if (msg!=null){
                            list.add(msg);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "msgId='" + msgId + '\'' +
                ", chatId='" + chatId + '\'' +
                ", loginCid='" + loginCid + '\'' +
                ", msgType=" + msgType +
                ", content=" + content +
                ", time=" + time +
                ", state=" + state +
                '}';
    }
}
