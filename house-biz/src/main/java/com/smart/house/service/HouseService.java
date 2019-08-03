package com.smart.house.service;

import com.google.common.base.Strings;
import com.smart.house.dao.HouseDao;
import com.smart.house.model.*;
import com.smart.house.page.PageData;
import com.smart.house.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class HouseService {
    /* ------------------------------房产模块------------------------------*/
    /**
     * 1,查询小区
     * 2，添加图片路径前缀
     * 3，构建分页结果
     */
    @Autowired
    private HouseDao houseDao;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private MailService mailService;


    @Value("${file.prefix}")
    private String imgPrefix;
    //查询房产列表
    public PageData<House> selectHouseList(House house, PageParams pageParams) {
        if(!Strings.isNullOrEmpty(house.getName())){  //小区名是否为空
            Community community=new Community();
            community.setName(house.getName());
            List<Community> communities=houseDao.selectCommunity(community);  //搜索小区
            if(!communities.isEmpty()){
                house.setCommunityId(communities.get(0).getId());  //设置小区号
            }
        }
        //获取房屋列表
        List<House> houseList= queryAndSetImg(house,pageParams);
        //获取房产总数
        Integer count=houseDao.selectPageCount(house);
        //返回pageData对象
        return PageData.buildPage(houseList,count,pageParams.getPageSize(),pageParams.getPageNum());
    }
        //查询房屋列表，添加图片路径前缀
    public List<House> queryAndSetImg(House house, PageParams pageParams) {
        List<House> houses =   houseDao.selectHouseList(house, pageParams);
        houses.forEach(h ->{
            //首张图片
            h.setFirstImg(imgPrefix + h.getFirstImg());
            //图片列表
            h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            //户型图
            h.setFloorPlanList(h.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
        });
        return houses;
    }

   //获取房产详情
    public House selectOneHouse(Integer id) {
        House house=new House();
        house.setId(id);
        //获取房厂列表
        List<House> houses=queryAndSetImg(house,PageParams.build(1,1));
        if(!houses.isEmpty()){
            return houses.get(0);
        }
        return null;
    }
    //获取房产拥有者
    public HouseUser getHouseUser(House house) {
         HouseUser houseUser=houseDao.selectHouseUser(house.getId(),house.getUserId(),house.getType());
         return houseUser;
    }
    //添加用户留言
    public void addUserMsg(UserMsg userMsg) {
        userMsg.setCreateTime(new Date(System.currentTimeMillis()));
        //插入用户留言
        houseDao.addHouseMsg(userMsg);
        //获取经纪人
        User  agent =agencyService.selectAgentDeail(userMsg.getAgentId());
        //发送邮件
        mailService.sendMail("来自用户"+userMsg.getEmail()+"的留言",userMsg.getMsg(),agent.getEmail());
    }
    //用户评星
    public void updateRating(Integer id, Double rating) {
    }
}
