package com.warrior.base.service;

import com.baomidou.mybatisplus.service.IService;
import com.warrior.base.entity.Notes;
import java.util.List;

public interface NotesService extends IService<Notes>{

    List<Notes> getNotesList();

    boolean updateStatus(int id,int status);

    boolean updateNote(int id,String note);

    boolean addNote(String note);
}
