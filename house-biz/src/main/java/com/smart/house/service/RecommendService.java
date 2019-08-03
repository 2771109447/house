package com.smart.house.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.smart.house.model.House;
import com.smart.house.page.PageParams;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    @Autowired
    private HouseService houseService;
    //计时器，每点击一次加1
    private  static  final String Hot_House_kry="hot_house";

    private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);

    /**
     * 热门房产推荐
       ·每点击详情页，计数器加一
       ·获取有序列表id,转换成list
       ·根据id的集合查询房产列表
     */
    public void increase(Integer id) {
        try {
            Jedis jedis = new Jedis("127.0.0.1");
            jedis.auth("281634");
            jedis.zincrby(Hot_House_kry, 1.0D, id + "");  //zset数据结构，有序列表
            jedis.zremrangeByRank(Hot_House_kry, 0, -11);// 0代表第一个元素,-1代表最后一个元素，保留热度由低到高末尾10个房产
            jedis.close();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

    }

    /**获取热门房产有序列表，并转换成list**/
    public List<Integer> getHot() {
        try {
            Jedis jedis = new Jedis("127.0.0.1");
            jedis.auth("281634");
            //获取有序列表10个元素
            Set<String> idSet = jedis.zrevrange(Hot_House_kry, 0, -1);
            jedis.close();
            //set抽取id转成list
            List<Integer> ids = idSet.stream().map(Integer::parseInt).collect(Collectors.toList());
            return ids;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Lists.newArrayList();
        }

    }
    /**根据list集合查询对于房产列表**/
    public List<House> getHotHouse(Integer size){
         House  house = new House();
         //获取热门房产列表
         List<Integer> list=getHot();
         list = list.subList(0, Math.min(list.size(), size));
        if (list.isEmpty()) {
            return Lists.newArrayList();
        }
        //初始化ids
        house.setIds(list);
        final List<Integer> order = list;
        //查询房产进行排序
        List<House> houses = houseService.queryAndSetImg(house, PageParams.build(size, 1));
        Ordering<House> houseSort = Ordering.natural().onResultOf(hs -> {
            return order.indexOf(hs.getId());
        });
        return houseSort.sortedCopy(houses);
    }
    /**首页新房推荐**/
    public List<House> selectRecommendHouses() {
        House house=new House(); //默认是按时间降序排列
        List<House> houses=houseService.queryAndSetImg(house,PageParams.build(8,1));
        return houses;
    }

}
