package com.imooc.order.mapper;

import com.imooc.order.pojo.Orders;

/**
 * @author chris
 */
public interface OrdersMapper {

    int deleteByPrimaryKey(String id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
}