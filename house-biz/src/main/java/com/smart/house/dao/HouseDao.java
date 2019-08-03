package com.smart.house.dao;

import com.smart.house.model.Community;
import com.smart.house.model.House;
import com.smart.house.model.HouseUser;
import com.smart.house.model.UserMsg;
import com.smart.house.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface HouseDao {
    //搜索房屋列表（分页）
    List<House> selectHouseList(@Param("house") House house, @Param("pageParams")PageParams pageParams);
   //查询房产总数
    Integer selectPageCount(@Param("house") House house);
    //查询小区
    List<Community> selectCommunity(Community community);
    //查询房产拥有者
    HouseUser selectHouseUser(@Param("id") Integer id,@Param("userId") Integer userId,@Param("type") Integer type);
    //插入用户留言
    Integer addHouseMsg(UserMsg userMsg);
}
