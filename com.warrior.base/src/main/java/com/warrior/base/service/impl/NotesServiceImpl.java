package com.warrior.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warrior.base.dao.NotesDao;
import com.warrior.base.entity.Notes;
import com.warrior.base.entity.User;
import com.warrior.base.service.NotesService;
import com.warrior.common.Contacts;
import com.warrior.common.service.WarriorBaseServiceImpl;
import com.warrior.common.web.WarriorSession;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotesServiceImpl extends WarriorBaseServiceImpl<NotesDao, Notes> implements NotesService {

    @Override
    public List<Notes> getNotesList() {
        User user = WarriorSession.getItem(getToken());
        EntityWrapper<Notes> ew = new EntityWrapper<Notes>();
        ew.setSqlSelect("id","note","status","create_time","over_time");
        ew.eq("user_id",user.getUid());
        ew.orderBy("status",true);
        ew.orderBy("create_time",false);
        return baseMapper.selectList(ew);
    }

    @Override
    public boolean updateStatus(int id, int status) {
        Notes notes = baseMapper.selectById(id);
        if (notes != null){
            notes.setStatus(status);
            if(status == Contacts.NOTE_STATUS_OVER){
                notes.setOverTime(new Date());
            }
            return baseMapper.updateById(notes) > 0;
        }
        return false;
    }

    @Override
    public boolean addNote(String note) {
        User user = WarriorSession.getItem(getToken());
        Notes notes = new Notes();
        notes.setUserId((int)user.getUid());
        notes.setNote(note);
        notes.setCreateTime(new Date());
        notes.setStatus(Contacts.NOTE_STATUS_NORMAL);
        return baseMapper.insert(notes) > 0;
    }

    public boolean updateNote(int id, String note) {
        Notes notes = baseMapper.selectById(id);
        if (notes != null){
            notes.setNote(note);
            return baseMapper.updateById(notes) > 0;
        }
        return false;
    }
}