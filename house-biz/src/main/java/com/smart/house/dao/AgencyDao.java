package com.smart.house.dao;

import com.smart.house.model.Agency;
import com.smart.house.model.User;
import com.smart.house.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgencyDao {
    //查询经纪人列表
    List<User> selectAgent(@Param("user") User user, @Param("pageParams")PageParams pageParams);
    //查询经纪人总数
    Integer selectAgentCount(@Param("user") User user);

    List<Agency> selectAgencyList(Agency agency);
}
