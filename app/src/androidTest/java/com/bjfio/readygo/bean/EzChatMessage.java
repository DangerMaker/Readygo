package com.bjfio.readygo.bean;

import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类
 * Created by inf on 2017/2/10.
 */

public class EzChatMessage{

    Cursor cursor;
    Map<String,Object> map = new HashMap<>();
    public EzChatMessage() {
    }

    public String chat_id;

    public String login_uid;

    public String author;

    public String msg_id;

    public long stime;

    public long ltime;

    public String msg;

    public String replyTo;

    public int mode;

    public String state;

    public int contain_pic;

    public int annotation;

    public int contain_at;

    public int notified;

    public String raw;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getLogin_uid() {
        return login_uid;
    }

    public void setLogin_uid(String login_uid) {
        this.login_uid = login_uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public long getStime() {
        return stime;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    public long getLtime() {
        return ltime;
    }

    public void setLtime(long ltime) {
        this.ltime = ltime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getContain_pic() {
        return contain_pic;
    }

    public void setContain_pic(int contain_pic) {
        this.contain_pic = contain_pic;
    }

    public int getAnnotation() {
        return annotation;
    }

    public void setAnnotation(int annotation) {
        this.annotation = annotation;
    }

    public int getContain_at() {
        return contain_at;
    }

    public void setContain_at(int contain_at) {
        this.contain_at = contain_at;
    }

    public int getNotified() {
        return notified;
    }

    public void setNotified(int notified) {
        this.notified = notified;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}
