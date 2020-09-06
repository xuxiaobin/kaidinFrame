${pojo.packageDeclaration}

import com.kaidin.appframe.service.dao.IBaseHibernateDao;
import com.kaidin.db.entity.${pojo.declarationName};

/**
${pojo.getClassJavaDoc("generated by Hibernate Tools ${version}", 0)}
${pojo.getClassJavaDoc("@date ${date}", 0)}
 */
public interface I${pojo.declarationName}Dao extends IBaseHibernateDao<${pojo.declarationName}> {
	String RESOURCE_NAME = "I${pojo.declarationName}Dao";
}
