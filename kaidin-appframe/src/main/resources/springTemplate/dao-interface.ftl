${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}
import com.kaidin.appframe.service.interfaces.IBaseDao;
import com.kaidin.db.entity.${pojo.getDeclarationName()};
/**
 * Home object for domain model class ${pojo.getDeclarationName()}
 */
public interface I${pojo.getDeclarationName()}Dao extends IBaseDao<${pojo.getDeclarationName()}> {
	public static final String RESOURCE_NAME = "I${pojo.getDeclarationName()}Dao";
}
