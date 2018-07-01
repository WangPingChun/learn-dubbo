package com.imooc.web.service.impl;

import com.imooc.item.service.ItemsService;
import com.imooc.order.service.OrdersService;
import com.imooc.web.curator.util.DistributedLock;
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
    private final DistributedLock distributedLock;

    @Autowired
    public ClusterServiceImpl(ItemsService itemsService, OrdersService ordersService, DistributedLock distributedLock) {
        this.itemsService = itemsService;
        this.ordersService = ordersService;
        this.distributedLock = distributedLock;
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
        // 执行订单流程之前获得当前业务的分布式锁
        distributedLock.getLock();
        int buyCounts = 6;
        // 判断库存
        final int stockCounts = itemsService.countItems(itemId);
        if (stockCounts < buyCounts) {
            log.info("库存剩余{}件,用户需求{}件,库存不足,订单创建失败", stockCounts, buyCounts);
            // 释放锁,让下一个请求获得锁
            distributedLock.releaseLock();
            return false;
        }

        // 模拟业务处理需要5秒
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 释放锁,让下一个请求获得锁
            distributedLock.releaseLock();
        }

        // 创建订单
        final boolean orderCreated = ordersService.createOrder(itemId);

        if (orderCreated) {
            log.info("订单创建成功");
            itemsService.displayReduceCount(itemId, buyCounts);
        } else {
            log.info("订单创建失败");
            // 释放锁,让下一个请求获得锁
            distributedLock.releaseLock();
            return false;
        }

        // 释放锁,让下一个请求获得锁
        distributedLock.releaseLock();

        return true;
    }
}
