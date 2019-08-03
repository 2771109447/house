package com.smart.house.controller;

import com.smart.house.model.Agency;
import com.smart.house.model.House;
import com.smart.house.model.User;
import com.smart.house.page.PageData;
import com.smart.house.page.PageParams;
import com.smart.house.service.AgencyService;
import com.smart.house.service.HouseService;
import com.smart.house.service.MailService;
import com.smart.house.service.RecommendService;
import com.smart.house.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("agency")
public class AgencyController {
    @Autowired
    private AgencyService agencyService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private MailService mailService;
    @Autowired
    private RecommendService recommendService;


    /* ------------------------------经纪人模块------------------------------*/
    /**
     *经纪人列表
     */
    @RequestMapping("agentList")
    public String agentList(Integer pageSize, Integer pageNum, Model model){
        PageData<User> pageData=agencyService.selectAgentList(PageParams.build(pageSize,pageNum));
        model.addAttribute("ps",pageData);
        //热门房产
        List<House> houseList=recommendService.getHotHouse(3);
        model.addAttribute("recomHouses",houseList);
        return "user/agent/agentList";
    }

    /**
     * 经纪人详情
        ·经纪人拥有的房产
     */
    @RequestMapping("agentDetail")
    public String agentDetail(Integer id, Model model,String successMsg){
        User agent=agencyService.selectAgentDeail(id);   //查询经纪人详情
        House house=new House();
        house.setUserId(id);
        house.setBookmarked(false);   //true:收藏；false:售卖
        //经纪人拥有的房产
        PageData<House> bindHouses=houseService.selectHouseList(house,PageParams.build(3,1));
        if (bindHouses!=null){
            model.addAttribute("bindHouses",bindHouses.getList());
        }
        if(successMsg!=null)
        {
            model.addAttribute("successMsg","留言成功");
        }
        //保存经纪人详情
        model.addAttribute("agent",agent);
        //热门房产
        List<House> houseList=recommendService.getHotHouse(3);
        model.addAttribute("recomHouses",houseList);
        return "user/agent/agentDetail";
    }

    /**用户留言**/
    @RequestMapping("/leaveMsg")
    public String agentMsg(Integer id,String msg,String name,String email, Model model){
        User agent =  agencyService.selectAgentDeail(id);
        //直接发送邮箱给经纪人
        mailService.sendMail("咨询", "email:"+email+",msg:"+msg, agent.getEmail());
        return "redirect:/agency/agentDetail?id="+id +"&successMsg=留言成功";
    }

    /* ------------------------------经纪机构模块------------------------------*/

    /**
     * 经济机构列表
     */
    @RequestMapping("agencyList")
    public String agencyList(Model model){
        List<Agency> agencyList = agencyService.selectAgencyList();
        model.addAttribute("agencyList", agencyList);
        //热门房产
        List<House> houseList=recommendService.getHotHouse(3);
        model.addAttribute("recomHouses",houseList);
        return "/user/agency/agencyList";
    }
    /**
     * 经济机构详情
     */
    @RequestMapping("agencyDetail")
    public String agencyDetail(Integer id,Model model ){
        Agency agency =  agencyService.selectAgency(id);
        model.addAttribute("agency", agency);
        //热门房产
        List<House> houseList=recommendService.getHotHouse(3);
        model.addAttribute("recomHouses",houseList);
        return "/user/agency/agencyDetail";
    }


//
//    @RequestMapping("agency/submit")
//    public String agencySubmit(Agency agency){
//        User user =  UserContext.getUser();
//        if (user == null || !Objects.equal(user.getEmail(), "spring_boot@163.com")) {//只有超级管理员可以添加,拟定spring_boot@163.com为超管
//            return "redirect:/accounts/signin?" + ResultMsg.successMsg("请先登录").asUrlParams();
//        }
//        agencyService.add(agency);
//        return "redirect:/index?" + ResultMsg.successMsg("创建成功").asUrlParams();
//    }

}
