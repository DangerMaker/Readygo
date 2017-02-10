package com.bjfio.readygo.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */
public class BeautyInfo {
    List<Task> list;

    public BeautyInfo() {
        this.list = new ArrayList<>();
    }

    public List<Task> getList() {
        return list;
    }

    public void setList(List<Task> list) {
        this.list = list;
    }
}
