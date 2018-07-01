package com.imooc.item.mapper;


import com.imooc.item.pojo.Items;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author chris
 */
public interface ItemsMapper extends Mapper<Items> {
    /**
     * 更新商品库存.
     *
     * @param record
     * @return
     */
    int reduceCounts(Items record);
}