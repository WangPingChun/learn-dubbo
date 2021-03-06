package com.imooc.order.pojo;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.Id;

/**
 * @author : chris
 * 2018-06-30
 */
public class Orders {
    @Id
    private String id;

    private String orderNum;

    private String itemId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
