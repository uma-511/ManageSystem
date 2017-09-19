package com.warrior.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.warrior.user.dao.UserDao;
import com.warrior.user.entity.User;
import com.warrior.user.entity.UserEntity;
import com.warrior.user.service.UserService;
import com.warrior.util.common.*;
import com.warrior.util.exception.WarriorException;
import com.warrior.util.dao.WarriorBaseMapper;
import com.warrior.util.service.WarriorBaseServiceImpl;
import com.warrior.util.shiro.MD5;
import com.warrior.util.web.SessionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends WarriorBaseServiceImpl<User> implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    protected WarriorBaseMapper<User> getBaseMapper() {
        return (WarriorBaseMapper<User>)userDao;
    }

    /**
     * 获取当前用户
     * @return
     */
    public UserEntity getCurrentUser(){
        UserEntity entity = SessionUtil.getValue(Contacts.SESSION_USER);
        return entity;
    }

    /**
     * 用户登录
     * @param userName
     * @param pwd
     */
    public UserEntity login(String userName,String pwd){
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName,pwd);
            subject.login(token);
        } catch (UnknownAccountException e){
            throw new WarriorException("账号不存在！",e);
        } catch (LockedAccountException e){
            throw new WarriorException("账号被锁定或被删除！",e);
        } catch (IncorrectCredentialsException e){
            throw new WarriorException("密码错误！");
        } catch (ExcessiveAttemptsException e){
            throw new WarriorException("密码错误5次账户锁定10分钟！",e);
        } catch (AuthenticationException e) {
            throw new WarriorException("登录认证失败！",e);
        }
        User user = getByUserName(userName);
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(user,entity);
        SessionUtil.setAttr(Contacts.SESSION_USER,entity);
        return entity;
    }

    /**
     * 用户登出
     */
    public void logOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    public User getById(long id){
        return userDao.selectByPrimaryKey(id);
    }
    /**
     * 根据属性查找
     * @param userName
     * @return
     */
    public User getByUserName(String userName){
        return userDao.getByUserName(userName);
    }

    /**
     * 按条件分页查询用户信息
     * @param userName
     * @param userType
     * @param status
     * @param startTime
     * @param endTime
     * @param page page <= 0 不分页
     * @param rows rows <= 0 不分页
     * @return
     */
    public List<User> getUserList(String userName, Integer userType, Integer status, Date startTime,Date endTime,Integer page,Integer rows){
        QueryParams params = new QueryParams();
        params.addStrParam("userName","%"+userName+"%")
                .addNumParam("userType",userType)
                .addNumParam("status",status)
                .addDateParam("startTime",startTime)
                .addDateParam("endTime",endTime);
        if ((page != null && page > 0) && (rows != null && rows > 0)){
            PageHelper.startPage(page,rows);
        }
        return userDao.getUserList(params);
    }

    @Override
    public User insert(User user) {
        user.setSalt(MD5.genSalt());
        user.setPassWord(MD5.genMd5(user.getPassWord(),user.genCredentialsSalt()));
        return super.insert(user);
    }
}