package com.smart.house.service;

import com.smart.house.dao.AgencyDao;
import com.smart.house.model.Agency;
import com.smart.house.model.User;
import com.smart.house.page.PageData;
import com.smart.house.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {
    @Autowired
    private AgencyDao agncyDao;
    @Value("${file.prefix}")
    private String  imgPrefix;

    /* ------------------------------经纪人模块------------------------------*/
    /**通过id获取经纪人**/
    public User selectAgentDeail(Integer userId) {
        User user=new User();
        user.setId(userId);
        user.setType(2);            //经纪人
        List<User> agentList=agncyDao.selectAgent(user, PageParams.build(1,1));
        setImg(agentList);  //添加经纪人头像前缀
        if(!agentList.isEmpty()){
            return  agentList.get(0);
        }
        return null;
    }
    //添加经纪人头像前缀
    private void setImg(List<User> list) {
        list.forEach(i -> {
            i.setAvatar(imgPrefix + i.getAvatar());
        });
    }

    /**分页查询经纪人列表**/
    public PageData<User> selectAgentList(PageParams pageParams) {
        User user=new User();
        List<User> agents=agncyDao.selectAgent(user,pageParams);
        setImg(agents);  //添加经纪人头像前缀

        Integer count =agncyDao.selectAgentCount(user);
        return PageData.buildPage(agents,count,pageParams.getPageSize(),pageParams.getPageNum());

    }
    /* ------------------------------经纪机构模块------------------------------*/

    /**查询所有经纪机构**/
    public List<Agency> selectAgencyList() {
        Agency agency=new Agency();
        List<Agency> agencyList=agncyDao.selectAgencyList(agency);
        return agencyList;
    }
    /**查询所有经纪机构**/
    public Agency selectAgency(Integer id) {
        Agency agency=new Agency();
        agency.setId(id);
        List<Agency> agencyList=agncyDao.selectAgencyList(agency);
        if (!agencyList.isEmpty()){
            return agencyList.get(0);
        }
        return null;
    }
}
