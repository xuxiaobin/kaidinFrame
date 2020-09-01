${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}

import javax.ejb.Local;

import com.kaidin.appframe.dao.IDao; 
import com.kaidin.gen.entity.${pojo.getDeclarationName()};
/**
 * Home object for domain model class ${pojo.getDeclarationName()}
 */
@Local
public interface I${pojo.getDeclarationName()}Dao extends IDao<${pojo.getDeclarationName()}> {

}