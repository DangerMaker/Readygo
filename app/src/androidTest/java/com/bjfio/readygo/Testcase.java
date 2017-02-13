package com.bjfio.readygo;

import android.test.AndroidTestCase;

import com.bjfio.readygo.bean.EzChatMessage;
import com.bjfio.readygo.utils.SystemUtils;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public class Testcase extends AndroidTestCase {

    IMDao imDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        imDao = IMDao.getInstance(getContext());
    }

    public void testInsertMsg() {
        EzChatMessage message = new EzChatMessage();
        message.setChat_id("1");
        message.setLogin_uid("2");
        message.setMsg_id("3");
        message.setAuthor("me");
        long ltime = System.currentTimeMillis();
        message.setStime(ltime);
        message.setLtime(ltime);
        message.setMsg("this is a json");
        message.setReplyTo("who");
        message.setMode(0);
        message.setState("0");
        message.setAnnotation(0);
        message.setContain_pic(0);
        message.setContain_at(0);
        message.setNotified(0);
        message.setRaw("this is a json");

        boolean result = imDao.insertMsg(message);
        Assert.assertEquals(result, true);


    }

    public void testDeleteMsg(){
        boolean result = imDao.deleteMsg("3");
        Assert.assertEquals(result, false);
    }

    public void testDeleteByChatId(){
        boolean result = imDao.deleteChat("1");
        Assert.assertEquals(result, false);
    }

    public void testGetList(){
        List<EzChatMessage> list = imDao.getChatHistory("2","1");
        Assert.assertEquals(list.size(), 5);
    }

    public void testUpdateMsgStateToSuccess() {
        long time = 1486883187707l;
        boolean result = imDao.updateMsgStateToSuccess(time,"9");
        Assert.assertEquals(result, true);
    }

    public void testUpdateMsgStateToFailed(){
        long time = 1486882818899l;
        boolean result = imDao.updateMsgStateToFailed(time);
        Assert.assertEquals(result, true);
    }

    public void test() {
        Assert.assertEquals(1, 1);
    }

}