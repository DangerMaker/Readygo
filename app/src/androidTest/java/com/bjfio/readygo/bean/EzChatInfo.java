package com.bjfio.readygo.bean;

import android.database.Cursor;

import java.util.List;

/**
 * 会话信息
 * Created by inf on 2017/2/9.
 */

public class EzChatInfo{

    /**
     * 是否是群会话
     */
    private boolean isGroup;

    /**
     * 是否设置了免打扰
     */
    private boolean isMute;



    /**通过cursor构造一个实例
     * @param cursor
     */
    public EzChatInfo(Cursor cursor){

    }

    public void updateDB() {

    }

    public List<EzChatMessage> unreadMessages() {
        return null;
    }

    public boolean isEqualChat(EzChatInfo chat) {
        return false;
    }

    public EzChatMessage getLastMessage() {
        return null;
    }
}
