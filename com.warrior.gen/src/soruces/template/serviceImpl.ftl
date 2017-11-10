package ${packageName}.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.common.service.impl.WarriorBaseServiceImpl;
import org.springframework.stereotype.Service;
import ${packageName}.service.${entityName}Service;
import ${packageName}.dao.${entityName}Dao;
import ${packageName}.entity.${entityName};

/**
* The type Role service.
*/
@Service
public class ${className} extends WarriorBaseServiceImpl<${entityName}Dao, ${entityName}> implements ${entityName}Service {

    public Page<${entityName}> getPageList(Page<${entityName}> page) {
        EntityWrapper<${entityName}> ew = new EntityWrapper<>();
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
   }

}