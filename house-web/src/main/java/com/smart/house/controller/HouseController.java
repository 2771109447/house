package com.smart.house.controller;


import com.smart.house.model.House;
import com.smart.house.model.User;
import com.smart.house.model.UserMsg;
import com.smart.house.model.HouseUser;
import com.smart.house.page.PageData;
import com.smart.house.page.PageParams;
import com.smart.house.service.AgencyService;
import com.smart.house.service.HouseService;
import com.smart.house.service.RecommendService;
import com.smart.house.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("house")
public class HouseController {

    @Autowired
    private HouseService houseService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private RecommendService recommendService;
    /* ------------------------------房产模块------------------------------*/
    /**
     * 首页房产推荐
     */
    @RequestMapping(" ")
    public String morenIndex(){
        return "redirect:house/index";
    }
    @RequestMapping("index")
    public String index(Model model){
        List<House> houses=recommendService.selectRecommendHouses();
        model.addAttribute("recomHouses",houses);
        return "homepage/index";
    }
    /**
     * 房产列表
        ·1，排序列表
        ·2，小区查询
        ·3，分类查询（1，售卖 2，出租）
     */
    @RequestMapping("list")
    public String houseList(Integer pageSize,Integer pageNum, House house, Model model){
     PageData<House> ps=houseService.selectHouseList(house, PageParams.build(pageSize,pageNum));
     model.addAttribute("ps",ps);      //分页对象及列表
     model.addAttribute("vo",house);   //为了传递排序
     //热门房产
     List<House> houseList=recommendService.getHotHouse(3);
     model.addAttribute("recomHouses",houseList);
     return "house/listing";
    }

    /**
     * 房产详情
      ·1，获取房产详情
      ·2，查询房产拥有者
      ·3，获取房产所属经纪人
      ·4，热门房产更新（每点击一次加一）
     */
    @RequestMapping("detail")
    public String houseDetail(Integer id,String successMsg, Model model){
        House house=houseService.selectOneHouse(id);    //查询房产详请
        HouseUser houseUser = houseService.getHouseUser(house);     //查询房产拥有者
        if(houseUser.getUserId()!=null&&!houseUser.getUserId().equals(0)){
            model.addAttribute("agent",agencyService.selectAgentDeail(houseUser.getUserId()));//获取房产经纪人
        }
        if(successMsg!=null){
            model.addAttribute("successMsg","留言成功");
        }
        model.addAttribute("house",house);
        //热门房产（点击更新）
        recommendService.increase(id);
        List<House> houseList=recommendService.getHotHouse(3);
        model.addAttribute("recomHouses",houseList);
        return "house/detail";
    }

    /**
     * 用户留言
     */
    @RequestMapping("leaveMsg")
    public String houseMsg(UserMsg userMsg){
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId()+"&successMsg=留言成功";
    }
    /**
     * 用户评星
     */
    @RequestMapping("rating")
    @ResponseBody
    public ResultMsg rating(Double rating, Integer id,Model model){
        houseService.updateRating(id,rating);
        return ResultMsg.successMsg("ok");
    }
//    /**
//     * 房产收藏
//     */
//    @RequestMapping("leaveMsg")
//    public String houseMsg(UserMsg userMsg, Model model, User agent,House house){
//        houseService.addUserMsg(userMsg);
//        return "redirect:/house/detail?id=" + userMsg.getHouseId()+"&successMsg=留言成功";
//    }

}
