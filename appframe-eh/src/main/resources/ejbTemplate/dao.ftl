${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}

//import javax.ejb.Stateless;
import com.kaidin.common.appframe.dao.BaseDaoImpl;
import com.kaidin.common.appframe.dao.IDaoContext;
import com.kaidin.db.dao.interfaces.I${pojo.getDeclarationName()}Dao;
import com.kaidin.db.entity.${pojo.getDeclarationName()};
/**
 * Dao implements for domain model class ${pojo.getDeclarationName()}.
 * @see ${pojo.getQualifiedDeclarationName()}
 */
//@Stateless(name="com.kaidin.gen.dao.interfaces.I${pojo.getDeclarationName()}Dao")
public class ${pojo.getDeclarationName()}DaoImpl extends BaseDaoImpl<${pojo.getDeclarationName()}> implements I${pojo.getDeclarationName()}Dao {

	public ${pojo.getDeclarationName()}DaoImpl(String jndiName, IDaoContext aDaoContext) throws Exception {
		super(jndiName, ${pojo.getDeclarationName()}.class, aDaoContext);
	}
	public ${pojo.getDeclarationName()}DaoImpl(IDaoContext context) throws Exception {
		super(${pojo.getDeclarationName()}.class, context);
	}
}