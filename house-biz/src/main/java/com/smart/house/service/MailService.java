package com.smart.house.service;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.smart.house.dao.UserDao;
import com.smart.house.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private UserDao userDao;

    @Value("${domain.name}")
    private String domainname;

    /*生成guava Cache 缓存<key,value>
    * 缓存超过100进行剔除未激活用户
    * 超时15分钟进行剔除未激活用户
    * */
    private final  Cache<String, String> registerCache =
            CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
                    .removalListener(new RemovalListener<String, String>() {

                        @Override
                        public void onRemoval(RemovalNotification<String, String> notification) {

                            String email = notification.getValue();
                            User user = new User();
                            user.setEmail(email);
                            List<User> targetUser = userDao.selectUsers(user);
                            if (!targetUser.isEmpty() && Objects.equal(targetUser.get(0).getEnable(), 0)) {
                                userDao.deleteUser(email);//  在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作

                            }

                        }
                    }).build();

    /**
     * 1，缓存key-email 关系
     * 2，使用spring mail发送邮件
     * 3，异步发送
     */
    @Async
    public void registerNotify(String email) throws UnsupportedEncodingException {
        String randomKey = RandomStringUtils.randomAlphabetic(10);  //生成key（10位随机字符串）
        registerCache.put(randomKey, email);                          //绑定key-email至Cache中
        String url = "http://" + domainname + "/user/activate?key=" + randomKey;    //生成链接
        String title="欢迎注册房产售卖平台!";
        sendMail(title, url, email);
    }

    /**
     * 邮件发送
     * title：邮件标题
     * url:邮件内容（链接）
     * email:邮箱地址
     */
    @Async
    public void sendMail(String title, String url, String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);        //邮件发件人
        message.setSubject(title);    //邮件标题
        message.setTo(email);         //收件人
        message.setText(url);         //发送内容
        mailSender.send(message);
    }


      //验证邮箱是否失效，并且激活
    public Boolean eable(String key) {
        String email=registerCache.getIfPresent(key);   //根据key获取email
        if (!StringUtils.isBlank(email)){
            User user=new User();
            user.setEmail(email);
            user.setEnable(1);    //激活用户
            userDao.updateUser(user); //更新用户
            registerCache.invalidate(key);//销毁key
            return true;
        }else {
            return false;
        }

    }
}
