package com.bjfio.readygo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bjfio.readygo.bean.EzChatInfo;
import com.bjfio.readygo.bean.EzChatMessage;
import com.bjfio.readygo.bean.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inf on 2017/2/8.
 */

public class IMDao implements IDatabaseHelper {

    private static IMDBHelper mHelper;
    private static IMDao mDao;

    private IMDao(Context context) {
        mHelper = IMDBHelper.getInstance(context);
    }

    public static IMDao getInstance(Context context) {
        if (mDao == null) {
            mDao = new IMDao(context);
        }
        return mDao;
    }

    @Override
    public boolean insertMsg(EzChatMessage msg) {
        ContentValues cv = getMsgMap(msg);
        boolean result = mHelper.insert(IMDBHelper.CHAT_WHATAPP_TABLE_NAME, null, cv);
        mHelper.close();
        return result;
    }

    /**
     * 删除某条消息
     *
     * @param msgId 消息id
     * @return
     */
    @Override
    public boolean deleteMsg(String msgId) {
        boolean result = mHelper.delete(IMDBHelper.CHAT_WHATAPP_TABLE_NAME, IMDBHelper.MSG_ID + "=?", new String[]{msgId});
        mHelper.close();
        return result;
    }

    /**
     * 删除某个聊天
     *
     * @param chatId 聊天id
     * @return
     */
    @Override
    public boolean deleteChat(String chatId) {
        return mHelper.delete(IMDBHelper.CHAT_WHATAPP_TABLE_NAME, IMDBHelper.CHAT_ID + "=?", new String[]{chatId});
    }

    @Override
    public List<EzChatMessage> getChatHistory(String loginUid,String chat_id) {
        String sql = "select * from "
                + IMDBHelper.CHAT_WHATAPP_TABLE_NAME
                + " where " + IMDBHelper.LOGIN_UID + "= " + "\'" + loginUid + "\'"
                + " and " + IMDBHelper.CHAT_ID + "= " + "\'" + chat_id + "\'" + "order by stime asc";

        Cursor cursor = mHelper.rawQuery(sql, null);
        if (cursor != null) {
            List<EzChatMessage> temp = new ArrayList<>();

            while (cursor.moveToNext()) {
                EzChatMessage message = new EzChatMessage(cursor);
                temp.add(message);
            }
            return temp;
        }
        return null;
    }

    /**
     * 发送消息成功更新state
     *
     * @param ltime
     * @param msgId
     * @return
     */
    public boolean updateMsgStateToSuccess(long ltime, String msgId) {
        ContentValues values = new ContentValues();
        values.put(IMDBHelper.STATE, 2);
        values.put(IMDBHelper.MSG_ID, msgId);

        return mHelper.update(IMDBHelper.CHAT_WHATAPP_TABLE_NAME, values, IMDBHelper.LTIME + "=?", new String[]{ltime + ""});
    }

    public boolean updateMsgStateToFailed(long ltime) {
        ContentValues values = new ContentValues();
        values.put("state", 1);
        return mHelper.update(IMDBHelper.CHAT_WHATAPP_TABLE_NAME, values, IMDBHelper.LTIME + "=?", new String[]{ltime + ""});
    }

    @Override
    public List<EzChatMessage> getUnreadMessages(String loginUid) {
        String sql = "select " + IMDBHelper.UNREAD_NUMBER + " from " + IMDBHelper.CHAT_INFO_TABLE_NAME
                + " where " + IMDBHelper.LOGIN_UID + "= \'" + loginUid + "\'";
        return null;
    }

    @Override
    public boolean insertUser(UserModel user) {
        return false;
    }

    @Override
    public boolean deleteUser(String uid) {
        return false;
    }

    @Override
    public boolean updateUserInfo(UserModel user) {
        return false;
    }

    @Override
    public boolean getUserInfo(String uid) {
        return false;
    }

    @Override
    public boolean insertChatInfo(EzChatInfo info) {
        return false;
    }

    @Override
    public boolean deleteChatInfo(String chatId) {
        return false;
    }

    @Override
    public boolean updateChatInfo(EzChatInfo info) {
        return false;
    }

    @Override
    public boolean getChatInfo(String chatId) {
        return false;
    }

    @Override
    public List<EzChatInfo> getChatList() {
        return null;
    }

    public ContentValues getMsgMap(EzChatMessage msg) {
        ContentValues values = new ContentValues();
        values.put("chat_id", msg.getChat_id());
        values.put("login_uid", msg.getLogin_uid());
        values.put("author", msg.getAuthor());
        values.put("msg_id", msg.getMsg_id());
        values.put("stime", msg.getStime());
        values.put("ltime", msg.getLtime());
        values.put("msg", msg.getMsg());
        values.put("replyTo", msg.getReplyTo());
        values.put("mode", msg.getMode());
        values.put("state", msg.getState());
        values.put("contain_pic", msg.getContain_pic());
        values.put("annotation", msg.getAnnotation());
        values.put("contain_at", msg.getContain_at());
        values.put("notified", msg.getNotified());
        values.put("raw", msg.getRaw());
        return values;
    }

}
