package com.imooc.web.service.impl;

import com.imooc.item.service.ItemsService;
import com.imooc.order.service.OrdersService;
import com.imooc.web.service.ClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : chris
 * 2018-06-30
 */
@Service("clusterService")
public class ClusterServiceImpl implements ClusterService {
    private static final Logger log = LoggerFactory.getLogger(ClusterServiceImpl.class);
    private final ItemsService itemsService;
    private final OrdersService ordersService;

    @Autowired
    public ClusterServiceImpl(ItemsService itemsService, OrdersService ordersService) {
        this.itemsService = itemsService;
        this.ordersService = ordersService;
    }

    @Override
    public void doBuyItem(String itemId) {
        // 减少库存
        itemsService.displayReduceCount(itemId, 1);
        // 创建订单
        ordersService.createOrder(itemId);
    }

    @Override
    public boolean displayBuy(String itemId) {
        int buyCounts = 5;
        // 判断库存
        final int stockCounts = itemsService.countItems(itemId);
        if (stockCounts < buyCounts) {
            log.info("库存剩余{}件,用户需求{}件,库存不足,订单创建失败", stockCounts, buyCounts);
            return false;
        }
        // 创建订单
        final boolean orderCreated = ordersService.createOrder(itemId);

        if (orderCreated) {
            log.info("订单创建成功");
            itemsService.displayReduceCount(itemId, buyCounts);
        } else {
            log.info("订单创建失败");
            return false;
        }
        return true;
    }
}
