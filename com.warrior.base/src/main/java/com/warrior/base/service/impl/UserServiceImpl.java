package com.warrior.base.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.base.dao.UserDao;
import com.warrior.base.entity.User;
import com.warrior.common.Contacts;
import com.warrior.common.model.UserModel;
import com.warrior.base.service.UserService;
import com.warrior.common.exception.WarriorException;
import com.warrior.common.service.impl.WarriorBaseServiceImpl;
import com.warrior.util.shiro.MD5;
import com.warrior.common.web.SessionUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j
@Service
public class UserServiceImpl extends WarriorBaseServiceImpl<UserDao,User> implements UserService{

    /**
     * 获取当前用户
     * @return
     */
    public UserModel getCurrentUser(){
        UserModel entity = SessionUtil.getValue(Contacts.SESSION_USER);
        return entity;
    }

    /**
     * 用户登录
     * @param userName
     * @param pwd
     */
    public UserModel login(String userName, String pwd){
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
        UserModel entity = new UserModel();
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

    /**
     * 根据属性查找
     * @param userName
     * @return
     */
    public User getByUserName(String userName){
        return baseMapper.getByUserName(userName);
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
    public Page<User> getUserList(String userName, int userType, int status, Date startTime, Date endTime, int page, int rows){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        if(!StringUtils.isBlank(userName)){
            wrapper.like("user_name",userName, SqlLike.DEFAULT);
        }
        if (userType != -1){
            wrapper.eq("user_type",userType);
        }
        if (status != -1){
            wrapper.eq("status",status);
        }
        if (startTime != null){
            wrapper.ge("start_time",startTime);
        }
        if (endTime != null){
            wrapper.le("end_time",endTime);
        }
        Page<User> paging = new Page(page,rows);
        paging.setRecords(baseMapper.getUserList(paging,wrapper));
        return paging;
    }

    @Override
    public boolean insert(User user) {
        user.setSalt(MD5.genSalt());
        user.setPassWord(MD5.genMd5(user.getPassWord(),user.genCredentialsSalt()));
        user.setUpdateTime(new Date());
        return super.insert(user);
    }

    public boolean modified(User user) {
        user.setUpdateTime(new Date());
        return super.insertOrUpdate(user);
    }

    public boolean delete(long id) {
        User user = baseMapper.selectById(id);
        if (StringUtils.equals("admin",user.getUserName())){
            throw new WarriorException("admin账户不能删除！");
        }
        return baseMapper.deleteById(id) > 0 ? true : false;
    }

    public boolean updatePassWord(String oldPw,String newPw){
        UserModel entity = SessionUtil.getValue(Contacts.SESSION_USER);
        User user = baseMapper.selectById(entity.getUid());
        if (!StringUtils.equals(MD5.genMd5(oldPw,user.genCredentialsSalt()),user.getPassWord())){
            throw new WarriorException("原密码不正确！");
        }
        user.setPassWord(MD5.genMd5(newPw,user.genCredentialsSalt()));

        return baseMapper.updateById(user) > 0 ? true : false;
    }
}