package com.imooc.web.service;

/**
 * @author : chris
 * 2018-06-30
 */
public interface ClusterService {
    /**
     * 购买商品.
     * @param itemId
     */
    void doBuyItem(String itemId);

    /**
     *
     * @param itemId
     * @return
     */
    boolean displayBuy(String itemId);
}
