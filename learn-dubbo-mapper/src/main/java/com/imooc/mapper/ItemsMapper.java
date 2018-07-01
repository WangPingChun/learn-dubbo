package com.imooc.mapper;

import com.imooc.pojo.Items;

public interface ItemsMapper {


    int deleteByPrimaryKey(String id);

    int insert(Items record);

    int insertSelective(Items record);


    Items selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Items record);

    int updateByPrimaryKey(Items record);

    int reduceCounts(Items record);
}