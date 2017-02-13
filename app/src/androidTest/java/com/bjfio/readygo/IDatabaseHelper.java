package com.bjfio.readygo;


import com.bjfio.readygo.bean.EzChatInfo;
import com.bjfio.readygo.bean.EzChatMessage;
import com.bjfio.readygo.bean.Msg;
import com.bjfio.readygo.bean.UserModel;

import java.util.List;

/**
 * Created by inf on 2017/2/9.
 */
public interface IDatabaseHelper {

    //***** 会话表的操作 ******//
    boolean insertMsg(EzChatMessage msg);

    boolean deleteMsg(String msgId);

    boolean deleteChat(String chatId);

    List<EzChatMessage> getChatHistory(String loginUid,String chatId);

    boolean updateMsgStateToSuccess(long ltime, String msgId);

    boolean updateMsgStateToFailed(long ltime);

    List<EzChatMessage> getUnreadMessages(String loginUid);

    //****  用户信息表的操作 ******//

    boolean insertUser(UserModel user);
    boolean deleteUser(String uid);
    boolean updateUserInfo(UserModel user);
    boolean getUserInfo(String uid);

    //***** 会话信息表的操作 ******//

    boolean insertChatInfo(EzChatInfo info);
    boolean deleteChatInfo(String chatId);
    boolean updateChatInfo(EzChatInfo info);
    boolean getChatInfo(String chatId);
    List<EzChatInfo> getChatList();
}
