package com.bjfio.readygo.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserModel implements Serializable{

    private String sortLetters;
    private String name;
    private String Uid;
    private String picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String image) {
        this.picture = image;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "sortLetters='" + sortLetters + '\'' +
                ", name='" + name + '\'' +
                ", Uid='" + Uid + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }

    public static List<UserModel> getUsers(String json) {
        List<UserModel> list=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(json);
            UserModel model=null;
            for(int i=0;array!=null&&i<array.length();i++){
                JSONObject object=array.optJSONObject(i);
                if (object!=null){
                    model=new UserModel();
                    model.setName(object.optString("name"));
                    model.setUid(object.optString("Uid"));
                    String picture=object.optString("picture");
                    if(!"[]".equals(picture)){
                        model.setPicture(picture);
                    }
                    list.add(model);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
