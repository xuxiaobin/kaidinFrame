// Generated ${date} by Hibernate Tools ${version}
${pojo.getPackageDeclaration()}

 /*
 * Home object for domain model class ${entity_name}.
 * @author shenli (shenl@certusnet.com.cn)
 */
 
import javax.ejb.Local;

import com.certus.appframe.dao.IDao; 
import com.certus.isa.entity.${entity_name}};

@Local
public interface I${entity_name}Dao extends IDao<${entity_name}> {

}