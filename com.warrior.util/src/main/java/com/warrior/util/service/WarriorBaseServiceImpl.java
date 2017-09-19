package com.warrior.util.service;

import com.warrior.util.dao.WarriorBaseMapper;
import com.warrior.util.exception.WarriorException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class WarriorBaseServiceImpl<T> implements WarriorBaseService<T> {
    /**
     * 设置Dao
     * @return
     */
    protected abstract WarriorBaseMapper<T> getBaseMapper();

    /**
     * 新增信息
     * @param t
     * @return
     */
    @Transactional
    public T insert(T t){
        int ret = getBaseMapper().insertUseGeneratedKeys(t);
        if(ret <=0 ){
            throw new WarriorException("数据新增失败！");
        }
        return t;
    }

    /**
     * 修改信息
     * @param t
     * @return
     */
    @Transactional
    public T modified(T t){
        int ret = getBaseMapper().updateByPrimaryKey(t);
        if (ret <= 0){
            throw new WarriorException("数据修改失败！");
        }
        return t;
    }

    /**
     * 删除信息
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(long id){
        T t = getBaseMapper().selectByPrimaryKey(id);
        if(t == null){
            throw new WarriorException("找不到相关数据！");
        }
        int ret = getBaseMapper().delete(t);
        if (ret <=0 ){
            throw new WarriorException("数据删除失败！");
        }
        return true;
    }

    /**
     * 根据主键获取信息
     * @param id
     * @return
     */
    public T get(long id){
        T t = getBaseMapper().selectByPrimaryKey(id);
        if(t == null){
            throw new WarriorException("找不到相关数据！");
        }
        return t;
    }

    /**
     * 获取所有信息
     * @return
     */
    public List<T> getList(){
        return getBaseMapper().selectAll();
    }

}
