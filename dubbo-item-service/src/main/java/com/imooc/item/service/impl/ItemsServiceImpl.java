package com.imooc.item.service.impl;

import com.imooc.item.mapper.ItemsMapper;
import com.imooc.item.pojo.Items;
import com.imooc.item.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : chris
 * 2018-06-30
 */
@Service("itemsService")
public class ItemsServiceImpl implements ItemsService {
    private final ItemsMapper itemsMapper;

    @Autowired
    public ItemsServiceImpl(ItemsMapper itemsMapper) {
        this.itemsMapper = itemsMapper;
    }

    @Override
    public Items getItem(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public int countItems(String itemId) {
        final Items item = getItem(itemId);
        return item.getCounts();
    }

    @Override
    public void displayReduceCount(String itemId, int buyCounts) {
        final Items items = new Items();
        items.setId(itemId);
        items.setBuyCounts(buyCounts);
        itemsMapper.reduceCounts(items);
    }
}
