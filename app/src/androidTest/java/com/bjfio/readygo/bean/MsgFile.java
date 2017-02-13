package com.bjfio.readygo.bean;

import android.text.TextUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 19082 on 2016/6/14.
 */
public class MsgFile {

    public static final int TYPE_IMAGE=0;
    public static final int TYPE_AUDIO=1;
    /**
     * "fid": "2134",
     "uid": "1",
     "filename": "6.jpg",
     "uri": "public://6_2.jpg",
     "filemime": "image/jpeg",
     "filesize": "86283",
     "status": "1",
     "timestamp": "1465953050",
     "rdf_mapping": [],
     "display": "1",
     "description": null
     *
     * */
    private String url;
    private int type;
    private String fid;
    private String fileMime;
    private String uri;
    private String fileName;


    public static MsgFile parseFile(String json){
        MsgFile file=new MsgFile();
        if (!TextUtils.isEmpty(json)){
            try {
                JSONArray array=new JSONArray(json);
                if (array!=null){
                    for(int i=0;i<array.length();i++){
                        JSONObject o=array.optJSONObject(i);
                        String mime=o.optString("filemime");
                        String fileName=o.optString("filename");
                        file.setUri(o.optString("uri"));
                        file.setFid(o.optString("fid"));
                        file.setFileMime(mime);
                        file.setFileName(fileName);
//                        file.setUrl(EzChatHelper.FILE_PATH+fileName);
                        if (mime.contains("image/")){
                            file.setType(TYPE_IMAGE);
                        }else{
                            file.setType(TYPE_AUDIO);
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFileMime() {
        return fileMime;
    }

    public void setFileMime(String fileMime) {
        this.fileMime = fileMime;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
