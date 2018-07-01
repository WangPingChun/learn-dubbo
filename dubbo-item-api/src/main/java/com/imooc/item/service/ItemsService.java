package com.imooc.item.service;


import com.imooc.item.pojo.Items;

/**
 * @author : chris
 * 2018-06-30
 */
public interface ItemsService {
    /**
     * 根据商品id获取商品.
     *
     * @param itemId
     * @return
     */
    Items getItem(String itemId);

    /**
     * 查询商品库存.
     *
     * @param itemId
     * @return
     */
    int countItems(String itemId);

    /**
     * 购买商品成功后减少库存.
     *
     * @param itemId
     * @param buyCounts
     */
    void displayReduceCount(String itemId, int buyCounts);
}
