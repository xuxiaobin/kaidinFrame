<#if packageName??>package ${packageName};</$if>

import com.kaidin.appframe.service.interfaces.IBaseDao;
import com.kaidin.db.entity.Entity${entityName ? cap_first};

/**
 * IEntity${entityName ? cap_first}Dao generated by kaidin
 * @version ${.now ? string("yyyy-MM-dd HH:mm:ss")}
 */
public interface IEntity${entityName ? cap_first}Dao extends IBaseMybatisDao<Entity${entityName ? cap_first}> {
	String FULL_RESOURCE_NAME = "${packageName}.IEntity${entityName ? cap_first}Dao";
	String RESOURCE_NAME	  = "IEntity${entityName ? cap_first}Dao";
}
