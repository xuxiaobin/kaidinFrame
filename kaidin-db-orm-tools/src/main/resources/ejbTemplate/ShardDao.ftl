package com.certus.isa.dao.impl;
// Generated ${date} by Hibernate Tools ${version}
 
 /*
 * Dao object for domain model class com.certus.isa.entity.${entity_name}.
 * @see com.certus.isa.entity.${entity_name}
 * @author shenli (shenl@certusnet.com.cn)
 */

import javax.ejb.Stateless;

import java.util.List;
import java.util.Map;
import com.certus.appframe.dao.shard.ShardId; 
import com.certus.appframe.dao.BaseShardDaoImpl;
import com.certus.appframe.dao.IDaoContext;
import com.certus.isa.dao.interfaces.I${entity_name}Dao;
import com.certus.isa.entity.${entity_name};
${pojo_import};

@Stateless(name="com.certus.isa.dao.interfaces.I${entity_name}Dao")
public class ${entity_name}DaoImpl extends BaseShardDaoImpl<${entity_name}> implements I${entity_name}Dao {

	public ${entity_name}DaoImpl()
	{   
		${pojo_map}
		super(${entity_name}.class, classMap);
	}
	
	public ${entity_name}DaoImpl(IDaoContext context){
	    ${pojo_map}
		super(${entity_name}.class, classMap, context);
	}
}