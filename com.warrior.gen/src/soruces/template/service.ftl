package ${packageName}.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import ${packageName}.entity.${entityName};

public interface ${className} extends IService<${entityName}>{

    Page<${entityName}> getPageList(Page<${entityName}> page);

}
