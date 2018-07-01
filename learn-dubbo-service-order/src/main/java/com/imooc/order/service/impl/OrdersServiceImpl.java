package com.imooc.order.service.impl;

import com.imooc.mapper.OrdersMapper;
import com.imooc.order.service.OrdersService;
import com.imooc.pojo.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author : chris
 * 2018-06-30
 */
@Service
public class OrdersServiceImpl implements OrdersService {
    private final static Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);
    private final OrdersMapper ordersMapper;

    @Autowired
    public OrdersServiceImpl(OrdersMapper ordersMapper) {
        this.ordersMapper = ordersMapper;
    }

    @Override
    public Orders getOrder(String orderId) {
        return ordersMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public boolean createOrder(String itemId) {

        final String oid = UUID.randomUUID().toString().replace("-", "");
        Orders orders = new Orders();
        orders.setId(oid);
        orders.setItemId(itemId);
        ordersMapper.insert(orders);

        log.info("订单创建成功");
        return true;
    }
}
