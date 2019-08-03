package com.smart.house.service;

        import com.google.common.collect.Lists;
        import com.smart.house.dao.UserDao;
        import com.smart.house.model.User;
        import com.smart.house.util.Salt;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.UnsupportedEncodingException;
        import java.util.Date;
        import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private  FileService fileService;
    @Autowired
    private MailService mailService;
    @Value("${file.prefix}")
    private String fileprefix;



    /**
     * 1，密码MD5加密，头像保存本地
     * 2，插入数据库，未激活
     * 3，生成key，绑定email
     * 4，发送邮件
     */
    /* ------------------------------用户注册------------------------------*/
    //添加用户，上传头像，邮箱发实链接
    @Transactional
    public Boolean addUser(User user) throws UnsupportedEncodingException {
        user.setPasswd(Salt.encryPassword(user.getPasswd()));      //密码加密
        List<String> imgList = fileService.getImgPaths(Lists.newArrayList(user.getAvatarFile()));  //获取相对路径列表
        if(!imgList.isEmpty()){
            user.setAvatar(imgList.get(0));    //设置文件相对路径
        }
        user.setCreateTime(new Date(System.currentTimeMillis()));    //注册时间
        user.setEnable(0);                   //暂未激活
        userDao.addUser(user);               //添加用户
        mailService.registerNotify(user.getEmail());     //邮箱验证
        return true;
    }
    //验证email是否过期
    public Boolean enable(String key) {
        return mailService.eable(key);
    }


    /* ------------------------------用户登入------------------------------*/
    //验证用户是否存在
    @Transactional
    public User selectUsers(String username, String password) {
        User user=new User();
        user.setEmail(username);
        user.setPasswd(Salt.encryPassword(password));
        user.setEnable(1);                                  //已激活
        List<User> userList=userDao.selectUsers(user);      //验证用户
        userList.forEach(user1 -> {
            user1.setAvatar(fileprefix+user1.getAvatar());  //拼接头像路径
        });
        if(!userList.isEmpty()){
            return userList.get(0);
        }else {
            return null;
        }
    }

    /* ------------------------------个人信息------------------------------*/

    /**
     * 1，更新用户
     * 2,获取更新后的用户
     * @param user
     */
    @Transactional
    public User updateUser(User user) {
        userDao.updateUser(user);       //更新用户
        List<User> userList=userDao.selectUsers(user);
        userList.forEach(user1 -> {
            user1.setAvatar(fileprefix+user1.getAvatar());  //拼接头像路径
        });
        User user1=userList.get(0);     //获取更新后的用户
        return user1;
    }
}
