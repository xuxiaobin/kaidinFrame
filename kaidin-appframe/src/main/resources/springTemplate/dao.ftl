${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.service.impl.BaseDaoSpringImpl;
import com.kaidin.db.dao.interfaces.I${pojo.getDeclarationName()}Dao;
import com.kaidin.db.entity.${pojo.getDeclarationName()};
/**
 * Dao implements for domain model class ${pojo.getDeclarationName()}.
 * @see ${pojo.getQualifiedDeclarationName()}
 */
@Repository(value="I${pojo.getDeclarationName()}Dao")
public class ${pojo.getDeclarationName()}DaoImpl extends BaseDaoSpringImpl<${pojo.getDeclarationName()}> implements I${pojo.getDeclarationName()}Dao {

	public ${pojo.getDeclarationName()}DaoImpl() throws Exception {
		super(${pojo.getDeclarationName()}.class);
	}
}
