package com.bjfio.readygo.bean;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 19082 on 2016/5/19.
 */
public class MsgContent {


    private String type;
    private String value;
    private String name;
    private String imageId;
    private String targetName;
    private String targetImageId;
    private String duration;
    private String lat,lng;
    private String address;
    private String ezAction;


    public class ContentType {
        public static final String TEXT = "text";
        public static final String IMAGE = "image";
        public static final String AUDIO = "audio";
        public static final String VIDEO = "video";
        public static final String LOCATION = "location";
        public static final String LINK = "link";
    }

    public static MsgContent parseJson(String json) {
        if (TextUtils.isEmpty(json) || json.equals("null"))
            return null;
        MsgContent msgContent = new MsgContent();
        try {
            JSONObject object = new JSONObject(json);
            msgContent.setImageId(object.optString("imageId"));
            msgContent.setValue(object.optString("value"));
            msgContent.setType(object.optString("type"));
            msgContent.setName(object.optString("name"));
            msgContent.setTargetImageId(object.optString("targetImageId"));
            msgContent.setTargetName(object.optString("targetName"));
            msgContent.setDruation(object.optString("duration"));
            msgContent.setLat(object.optString("lat"));
            msgContent.setLng(object.optString("lng"));
            msgContent.setAddr(object.optString("address"));
            msgContent.setEzAction(json);

            return msgContent;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MsgContent parseJsonFromServer(String json) {
        if (TextUtils.isEmpty(json) || json.equals("null")||json.equals("[]"))
            return null;
        MsgContent msgContent = new MsgContent();
        try {
            JSONArray array = new JSONArray(json);
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject root = array.optJSONObject(i);
                    if (root!=null){
                        String value=root.getString("value");
                        JSONObject object = new JSONObject(value);
                        msgContent.setImageId(object.optString("imageId"));
                        msgContent.setValue(object.optString("value"));
                        msgContent.setType(object.optString("type"));
                        msgContent.setName(object.optString("name"));
                        msgContent.setTargetImageId(object.optString("targetImageId"));
                        msgContent.setTargetName(object.optString("targetName"));
                        msgContent.setDruation(object.optString("duration"));
                        msgContent.setLat(object.optString("lat"));
                        msgContent.setLng(object.optString("lng"));
                        msgContent.setAddr(object.optString("address"));
                        if(object.optString("ezAction") != null && !object.optString("ezAction").equals("")){
                            msgContent.setEzAction(object.optString("ezAction"));
                        }
                        return msgContent;
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetImageId() {
        return targetImageId;
    }

    public void setTargetImageId(String targetImageId) {
        this.targetImageId = targetImageId;
    }

    public String getDruation() {
        return duration;
    }

    public void setDruation(String druation) {
        this.duration = druation;
    }

    public String getEzAction() {
        return ezAction;
    }

    public void setEzAction(String ezAction) {
        this.ezAction = ezAction;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddr() {
        return address;
    }

    public void setAddr(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MsgContent{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", imageId='" + imageId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", targetImageId='" + targetImageId + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
