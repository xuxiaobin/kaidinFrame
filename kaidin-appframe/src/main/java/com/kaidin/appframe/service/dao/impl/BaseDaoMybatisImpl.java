//package com.kaidin.appframe.service.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.ibatis.session.RowBounds;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.kaidin.appframe.config.AppframeConfig;
//import com.kaidin.appframe.service.dao.BaseEntity;
//import com.kaidin.appframe.exception.AppframeException;
//import com.kaidin.appframe.service.interfaces.IBaseMybatisDao;
//import com.kaidin.common.util.query.PageData;
//import com.kaidin.common.util.query.PageRequest;
//
///**
// * 操作数据库的sqlSessionTemplate实现
// *
// * @version 1.0
// * @author kaidin@foxmail.com
// * @date 2015-06-23
// */
//public abstract class BaseDaoMybatisImpl<T extends BaseEntity> implements IBaseMybatisDao<T> {
//	/** 传入sql的key */
//	protected static final String    SQL_KEY            = "value";
//	protected static final String    BASE_RESOURCE_NAME = IBaseMybatisDao.class.getName();
//	protected static final String    COUNT_NATIVE_SQL   = BASE_RESOURCE_NAME + ".countNativeSql";
//	protected static final String    QUERY_NATIVE_SQL   = BASE_RESOURCE_NAME + ".queryNativeSql";
//	protected static final String    UPDATE_NATIVE_SQL  = BASE_RESOURCE_NAME + ".updateNativeSql";
//	/** sql的id */
//	protected static final String    SAVE               = ".save";
//	protected static final String    DELETE             = ".delete";
//	protected static final String    DELETE_BY_ID       = ".deleteById";
//	protected static final String    DELETE_ENTITIES    = ".deleteEntities";
//	protected static final String    UPDATE             = ".update";
//	protected static final String    QUERY_ENTITIES     = ".queryEntities";
//	protected static final String    COUNT_ENTITIES     = ".countEntities";
//
//	/** 默认数量限制 */
//	protected static final RowBounds DEFAULT_ROW_BOUNDS = new RowBounds(0, AppframeConfig.getMaxQueryLimit());
//
//	/** 数据库操作session */
//	@Resource
//	protected SqlSessionTemplate     sqlSessionTemplate;
//	/** 资源名称 子类使用的时候设置 */
//	protected String                 resourceName;
//
//	/**
//	 * 构造函数
//	 *
//	 * @param resourceName
//	 */
//	public BaseDaoMybatisImpl(String resourceName) {
//		this.resourceName = resourceName;
//	}
//
//	// ================ add =======================
//	@Override
//	public T save(T entity) throws AppframeException {
//		sqlSessionTemplate.insert(resourceName + SAVE, entity);
//
//		return entity;
//	}
//
//	// ================ delete =======================
//	@Override
//	public int delete(T entity) throws AppframeException {
//		return sqlSessionTemplate.delete(resourceName + DELETE, entity);
//	}
//
//	@Override
//	public int deleteById(long id) throws AppframeException {
//		return sqlSessionTemplate.delete(resourceName + DELETE_BY_ID, id);
//	}
//
//	@Override
//	public int deleteEntities(String sqlWhere, Map<String, Object> parameter) throws AppframeException {
//		Map<String, Object> parameterMap = new HashMap<>();
//		parameterMap.putAll(parameter);
//		parameterMap.put(SQL_KEY, sqlWhere);
//		return sqlSessionTemplate.delete(resourceName + DELETE_ENTITIES, parameterMap);
//	}
//
//	// =================== update ==========================
//	@Override
//	public int update(T entity) throws AppframeException {
//		return sqlSessionTemplate.update(resourceName + UPDATE, entity);
//	}
//
//	// @Override
//	// public int update(T entity) throws AppframeException {
//	// return sqlSessionTemplate.delete(resourceName + UPDATE, entity);
//	// }
//	// ================ query entity =======================
//	@Override
//	@Transactional(readOnly = true)
//	public T queryEntity(long id) throws AppframeException {
//		Map<String, Object> parameter = new HashMap<>();
//		parameter.put(SQL_KEY, BaseEntity.P_id + " = #{id}");
//		parameter.put("id", id);
//		return sqlSessionTemplate.selectOne(resourceName + QUERY_ENTITIES, parameter);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public T queryEntity(String sqlWhere) throws AppframeException {
//		Map<String, Object> parameter = new HashMap<>();
//		parameter.put(SQL_KEY, sqlWhere);
//		return sqlSessionTemplate.selectOne(resourceName + QUERY_ENTITIES, parameter);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public T queryEntity(String sqlWhere, Map<String, Object> parameter) throws AppframeException {
//		parameter.put(SQL_KEY, sqlWhere);
//		return sqlSessionTemplate.selectOne(resourceName + QUERY_ENTITIES, parameter);
//	}
//
//	// ================ query entityList =======================
//	@Override
//	@Transactional(readOnly = true)
//	public List<T> queryEntities() throws AppframeException {
//		Map<String, Object> parameter = new HashMap<>();
//		parameter.put(SQL_KEY, "1 = 1");
//		return sqlSessionTemplate.selectList(resourceName + QUERY_ENTITIES, parameter, DEFAULT_ROW_BOUNDS);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<T> queryEntities(String sqlWhere) throws AppframeException {
//		Map<String, Object> parameter = new HashMap<>();
//		parameter.put(SQL_KEY, sqlWhere);
//		return sqlSessionTemplate.selectList(resourceName + QUERY_ENTITIES, parameter, DEFAULT_ROW_BOUNDS);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<T> queryEntities(String sqlWhere, int rowIndex, int rowNum) throws AppframeException {
//		Map<String, Object> parameter = new HashMap<>();
//		parameter.put(SQL_KEY, sqlWhere);
//		RowBounds rowBounds = buildRowBounds(rowIndex, rowNum);
//		return sqlSessionTemplate.selectList(resourceName + QUERY_ENTITIES, parameter, rowBounds);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<T> queryEntities(String sqlWhere, Map<String, Object> parameter) throws AppframeException {
//		parameter.put(SQL_KEY, sqlWhere);
//		return sqlSessionTemplate.selectList(resourceName + QUERY_ENTITIES, parameter, DEFAULT_ROW_BOUNDS);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<T> queryEntities(String sqlWhere, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
//		parameter.put(SQL_KEY, sqlWhere);
//		RowBounds rowBounds = buildRowBounds(rowIndex, rowNum);
//		return sqlSessionTemplate.selectList(resourceName + QUERY_ENTITIES, parameter, rowBounds);
//	}
//
//	// ================ query DataContainer =======================
//	@Override
//	@Transactional(readOnly = true)
//	public PageData<T> queryEntities(PageRequest pageLoadCfg) throws AppframeException {
//		PageData<T> result = new PageData<>(pageLoadCfg);
//
//		Map<String, Object> parameter = new HashMap<>();
//		parameter.put(SQL_KEY, "1 = 1");
//		int totalCount = sqlSessionTemplate.selectOne(resourceName + COUNT_ENTITIES, parameter);
//		result.setTotalCount(totalCount);
//
//		int offset = pageLoadCfg.getOffset() - 1;
//		if (totalCount > offset) {
//			RowBounds rowBounds = buildRowBounds(offset, pageLoadCfg.getLimit());
//			List<T> dataList = sqlSessionTemplate.selectList(resourceName + QUERY_ENTITIES, parameter, rowBounds);
//			result.setDataList(dataList);
//		}
//		result.setSuccess(true);
//
//		return result;
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public PageData<T> queryEntities(String sqlWhere, PageRequest pageLoadCfg) throws AppframeException {
//		PageData<T> result = new PageData<>(pageLoadCfg);
//
//		Map<String, Object> parameter = new HashMap<>();
//		parameter.put(SQL_KEY, sqlWhere);
//		int totalCount = sqlSessionTemplate.selectOne(resourceName + COUNT_ENTITIES, parameter);
//		result.setTotalCount(totalCount);
//
//		int offset = pageLoadCfg.getOffset() - 1;
//		if (totalCount > offset) {
//			RowBounds rowBounds = buildRowBounds(offset, pageLoadCfg.getLimit());
//			List<T> dataList = sqlSessionTemplate.selectList(resourceName + QUERY_ENTITIES, parameter, rowBounds);
//			result.setDataList(dataList);
//		}
//		result.setSuccess(true);
//
//		return result;
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public PageData<T> queryEntities(String sqlWhere, Map<String, Object> parameter, PageRequest pageLoadCfg) throws AppframeException {
//		PageData<T> result = new PageData<>(pageLoadCfg);
//
//		parameter.put(SQL_KEY, sqlWhere);
//		int totalCount = sqlSessionTemplate.selectOne(resourceName + COUNT_ENTITIES, parameter);
//		result.setTotalCount(totalCount);
//
//		int offset = pageLoadCfg.getOffset() - 1;
//		if (totalCount > offset) {
//			RowBounds rowBounds = buildRowBounds(offset, pageLoadCfg.getLimit());
//			List<T> dataList = sqlSessionTemplate.selectList(resourceName + QUERY_ENTITIES, parameter, rowBounds);
//			result.setDataList(dataList);
//		}
//		result.setSuccess(true);
//
//		return result;
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public int countNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
//		parameter.put(SQL_KEY, sql);
//		return sqlSessionTemplate.selectOne(COUNT_NATIVE_SQL, parameter);
//	}
//
//	/**
//	 * 因为对sql无约束，可能返回的结果类型不是安全的
//	 */
//	@Override
//	@Transactional(readOnly = true)
//	public List<Map<String, Object>> queryNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
//		parameter.put(SQL_KEY, sql);
//		return sqlSessionTemplate.selectList(QUERY_NATIVE_SQL, parameter, DEFAULT_ROW_BOUNDS);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<Map<String, Object>> queryNativeSql(String sql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
//		parameter.put(SQL_KEY, sql);
//		RowBounds rowBounds = buildRowBounds(rowIndex, rowNum);
//		return sqlSessionTemplate.selectList(QUERY_NATIVE_SQL, parameter, rowBounds);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public PageData<Map<String, Object>> queryNativeSql(String sql, Map<String, Object> parameter, PageRequest pageLoadCfg)
//	        throws AppframeException {
//		PageData<Map<String, Object>> result = new PageData<>();
//
//		parameter.put(SQL_KEY, sql);
//		int totalCount = sqlSessionTemplate.selectOne(COUNT_NATIVE_SQL, parameter);
//		result.setTotalCount(totalCount);
//
//		int offset = pageLoadCfg.getOffset() - 1;
//		if (totalCount > offset) {
//			RowBounds rowBounds = buildRowBounds(offset, pageLoadCfg.getLimit());
//			List<Map<String, Object>> dataList = sqlSessionTemplate.selectList(QUERY_NATIVE_SQL, parameter, rowBounds);
//			result.setDataList(dataList);
//		}
//		result.setSuccess(true);
//
//		return result;
//	}
//
//	private static RowBounds buildRowBounds(int rowIndex, int rowNum) {
//		int limit = Math.min(rowNum, AppframeConfig.getMaxQueryLimit());
//		return new RowBounds(rowIndex, limit);
//	}
//}
