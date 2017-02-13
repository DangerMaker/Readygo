package com.bjfio.readygo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/2/10.
 */
public class IMDBHelper extends SQLiteOpenHelper {

    public static final String CHAT_WHATAPP_TABLE_NAME = "chat_message_history";
    public static final String CHAT_USER_INFO_TABLE_NAME = "chat_user_info";
    public static final String CHAT_INFO_TABLE_NAME = "chat_info";

    private static final String IM_DB_NAME = "appuid_android_im";
    public static final int IM_VERSION = 1;

    //*************  消息表  *********//

    public static final String RAW = "raw";
    /**
     * chatId
     */
    public static final String CHAT_ID = "chat_id";
    /**
     * 发送者id
     */
    public static final String AUTHOR = "author";

    public static final String LOGIN_UID = "login_uid";
    /**
     * 消息id
     */
    public static final String MSG_ID = "msg_id";
    /**
     * 服务器时间
     */
    public static final String STIME = "stime";
    /**
     * 本地时间戳
     */
    public static final String LTIME = "ltime";
    /**
     * 消息体
     */
    public static final String MSG = "msg";
    /**
     * 原消息体（回复的消息体）
     */
    public static final String REPLY_TO = "replyTo";
    /**
     * 状态
     * 0.发送失败
     * 1.发送成功
     * 2.未读
     * 3.已读
     */
    public static final String STATE = "state";
    /**
     * 媒体类型
     * 1.媒体（不包含语音）
     * 2.链接
     * 3.文件
     * 4.位置
     */
    public static final String CONTAIN_PIC = "contain_pic";
    /**
     * 是否标注
     * 0.未标注
     * 1.已标注
     */
    public static final String ANNOTATION = "annotation";
    /**
     * 是否包含@信息
     * 0.否
     * 1.是
     */
    public static final String CONTAIN_AT = "contain_at";

    /**
     * 是否通知过
     * 0.否
     * 1.是
     */
    public static final String NOTIFIED = "notified";

    /**
     * 消息类型
     * 0 发送的消息
     * 1 接收的消息
     */
    public static final String MODE = "mode";

    //*************  消息表  end  *********//

    private static final String WHATSAPP_MSG_SQL = "CREATE TABLE IF NOT EXISTS " + CHAT_WHATAPP_TABLE_NAME + "("
            + "_id integer primary key,"
            + CHAT_ID + " TEXT,"
            + LOGIN_UID + " TEXT,"
            + AUTHOR + " TEXT,"
            + MSG_ID + " TEXT,"
            + STIME + " INTEGER,"
            + LTIME + " INTEGER,"
            + MSG + " TEXT,"
            + REPLY_TO + " TEXT,"
            + MODE + " INTEGER,"
            + STATE + " TEXT,"
            + CONTAIN_PIC + " INTEGER,"
            + ANNOTATION + " INTEGER,"
            + CONTAIN_AT + " INTEGER,"
            + NOTIFIED + " INTEGER,"
            + RAW + " TEXT"
            + ")";

    //******* 会话信息表  *******//
    public static final String AVATAR = "avatar";
    public static final String CHAT_NAME = "chat_name";
    public static final String MEMBERS = "members";
    public static final String CREATE_TIME = "create_time";
    public static final String CREATOR = "creator";
    public static final String MUTE = "mute";
    public static final String UNREAD_NUMBER = "unread_number";
    public static final String LAST_MSG_ID = "last_msg_id";

    /**
     * 会话信息表
     */
    private static final String CHAT_INFO_SQL = "CREATE TABLE IF NOT EXISTS " + CHAT_INFO_TABLE_NAME + "("
            + "_id integer primary key,"
            + CHAT_ID + " TEXT,"
            + AVATAR + " TEXT,"
            + CHAT_NAME + " TEXT,"
            + MEMBERS + " TEXT,"
            + CREATE_TIME + " INTEGER,"
            + CREATOR + " TEXT,"
            + LAST_MSG_ID + " TEXT,"
            + MUTE + " INTEGER,"
            + UNREAD_NUMBER + " INTEGER,"
            + RAW + " TEXT"
            + ")";

    //******** 会话信息表 end  *******//

    //******** 用户信息表  *******//
    public static final String UID = "mute";
    public static final String NAME = "name";
    public static final String NICK_NAME = "nick_name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String RELATIONSHIP = "relationship";
    private static final String CHAT_USER_INFO_SQL = "CREATE TABLE IF NOT EXISTS " + CHAT_USER_INFO_TABLE_NAME + "("
            + "_id integer primary key,"
            + UID + " TEXT,"
            + AVATAR + " TEXT,"
            + LOGIN_UID + " TEXT,"
            + NAME + " TEXT,"
            + NICK_NAME + " INTEGER,"
            + PHONE_NUMBER + " INTEGER,"
            + RELATIONSHIP + " TEXT,"
            + RAW + " TEXT"
            + ")";


    //******** 用户信息表  end *******//

    private static IMDBHelper helper;

    //new 时调用
    public IMDBHelper(Context context) {
        //name数据库文件名，游标工厂，数据库版本
        super(context, IM_DB_NAME, null, IM_VERSION);
        System.out.println("创建数据库成功");

    }

    public static IMDBHelper getInstance(Context context) {
        if (helper == null) {
            helper = new IMDBHelper(context);
        }
        return helper;
    }

    //数据库创建时调用
    @Override
    public void onCreate(SQLiteDatabase database) {
        //在创建数据库时创建表..这里可以增删改查，只要换里面的sql语句就可以了！！！
        database.execSQL(CHAT_INFO_SQL);
        database.execSQL(CHAT_USER_INFO_SQL);
        database.execSQL(WHATSAPP_MSG_SQL);
    }

    //数据库升级时此方法调用
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    public boolean insert(String table, String nullColumnHack,
                          ContentValues values) {
        boolean flag = false;
        SQLiteDatabase database;
        long id = -1;
        try {
            database = getWritableDatabase();
            id = database.insert(table, nullColumnHack, values);
            flag = (id != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean delete(String table, String whereClause, String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database;
        int affectedRow = 0;
        try {
            database = getWritableDatabase();
            affectedRow = database.delete(table, whereClause, whereArgs);
            flag = affectedRow == 0;
            System.out.println(affectedRow);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


    /**
     * 更新，成功返回true
     *
     * @param table
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean update(String table, ContentValues values,
                          String whereClause, String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database;
        int affectedRow = 0;
        try {
            database = getWritableDatabase();
            affectedRow = database
                    .update(table, values, whereClause, whereArgs);
            flag = affectedRow > 0;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return flag;
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {
        Cursor cursor = null;
        SQLiteDatabase database;
        try {
            database = getWritableDatabase();
            cursor = database.query(table, columns, selection, selectionArgs,
                    groupBy, having, orderBy);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return cursor;
    }


    public Cursor rawQuery(String sql, String[] selectionArgs) {
        Cursor cursor = null;
        SQLiteDatabase database;
        try {
            database = getWritableDatabase();
            cursor = database.rawQuery(sql, selectionArgs);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return cursor;
    }
}