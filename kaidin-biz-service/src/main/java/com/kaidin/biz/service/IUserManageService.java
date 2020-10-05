package com.kaidin.biz.service;

import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageRequest;
import com.kaidin.db.entity.EntityCfgUser;

/**
 * 用户管理接口
 * @author xuxiaobin	kaidin@foxmail.com
 */
public interface IUserManageService {
	/**
	 * 根据登陆用户名和密码获取数据库表记录
	 * @param loginName
	 * @param loginPasswd
	 * @return
	 */
	PageData<EntityCfgUser> login(String loginName, String loginPasswd);

	/**
	 * 退出登录
	 * @param userId
	 * @return
	 */
	boolean logout(long userId);
	
	/**
	 * 从session中获得登陆的用户数
	 * @return
	 */
	int getLoinUserCount();
	
	/**
	 * 注册用户
	 * @param newUser
	 * @return
	 */
	EntityCfgUser register(EntityCfgUser newUser);

	/**
	 * 销户（注意不是退出登录）
	 * @return
	 */
	boolean unregister(long userId);
	
	/**
	 * 获取数据库注册的用户名
	 * @param pageReq
	 * @return
	 */
	PageData<EntityCfgUser> queryUser(PageRequest pageReq);
	
	/**
	 * 重置密码，登陆之后应该强制用户修改
	 * @param userId
	 * @return
	 */
	boolean resetPassword(long userId);
	
	/**
	 * 锁住用户，不能登陆
	 * @param userId
	 * @return
	 */
	boolean lockUser(long userId);

	/**
	 * 解锁用户，可以登陆
	 * @param userId
	 * @return
	 */
	boolean unLockUser(long userId);
}
