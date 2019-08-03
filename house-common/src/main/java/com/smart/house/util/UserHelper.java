package com.smart.house.util;
import com.smart.house.model.User;
import org.apache.commons.lang3.StringUtils;

public class UserHelper {
    public static  ResultMsg validate(User user){
        if(StringUtils.isBlank(user.getEmail())){
            return  ResultMsg.errorMsg("Email有误");
        }
        if(StringUtils.isBlank(user.getUserName())){
            return  ResultMsg.errorMsg("名字有误");
        }
        if(StringUtils.isBlank(user.getConfirmPasswd())||StringUtils.isBlank(user.getPasswd())
              ||!user.getPasswd().equals(user.getConfirmPasswd())){
            return  ResultMsg.errorMsg("密码有误");
        }
         if(user.getPasswd().length()<6){
             return ResultMsg.errorMsg("密码大于5位");
         }
         return ResultMsg.successMsg("");
    }
}
