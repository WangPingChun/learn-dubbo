package com.imooc.order.service;

import com.imooc.order.pojo.Orders;

/**
 * @author : chris
 * 2018-06-30
 */
public interface OrdersService {
    /**
     * 根据订单id查询订单.
     *
     * @param orderId
     * @return
     */
    Orders getOrder(String orderId);

    /**
     * 下订单
     *
     * @param itemId
     * @return
     */
    boolean createOrder(String itemId);
}
