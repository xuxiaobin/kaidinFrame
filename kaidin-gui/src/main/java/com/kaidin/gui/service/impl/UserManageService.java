package com.kaidin.gui.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaidin.common.util.encrypt.EncryptUtil;
import com.kaidin.common.util.query.DataContainer;
import com.kaidin.common.util.query.PageLoadConfig;
import com.kaidin.db.dao.interfaces.IEntityCfgUserDao;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.service.interfaces.IUserManageService;
/**
 * 用户管理服务类
 * @author xuxiaobin	kaidin@foxmail.com
 * 
 */
@Service
public class UserManageService implements IUserManageService, HttpSessionListener {
	private static final transient Logger logger = LoggerFactory.getLogger(UserManageService.class);
	private static HashMap<String, HttpSession> SESSION_MAP = new HashMap<>();
	
	@Resource(name = IEntityCfgUserDao.RESOURCE_NAME)
	private IEntityCfgUserDao userDao;
	
	
	/**
	 * session创建的时候触发
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	/**
	 * session销毁的时候触发
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 根据登陆用户名和密码获取数据库表记录
	 * @param loginName
	 * @param loginPasswd
	 * @return
	 */
	@Override
	public DataContainer<EntityCfgUser> login(String loginName, String loginPasswd) {
		DataContainer<EntityCfgUser> result = new DataContainer<>();
		
		try {
			String hqlWhere = EntityCfgUser.P_Name + " =:loginName";
			EntityCfgUser user = userDao.queryEntity(hqlWhere, new String[]{"loginName"}, new Object[]{loginName});
			if (null != user) {
				if (GuiConstType.Status.LOCK == user.getStatus() && !"root".equals(loginName)) {
					result.setErrorCode(GuiConstType.ErrorCode.USER_LOCKED);
					result.setErrorMsg("用户错误登陆次数过多，已被锁定");
				} else if (EncryptUtil.md5(loginPasswd).equals(user.getPassword())) {
					result.setDataList(Arrays.asList(user));
					
					user.setLastLoginTime(new Date());	// 记录最后登陆时间
					user.setStatus(GuiConstType.ErrorCode.OK);
				} else {
					short status = user.getStatus();
					if (0 <= status) {
						status++;
						if ("root".equals(loginName)) {
							// 这里root不能被锁，否则没法解锁了
							result.setErrorMsg("密码不正确，已经尝试第" + status + "次密码");
						} else {
							if (GuiConstType.Status.ATTEMPT_MAX_TIMES <= status) {
								// 尝试的次数达到最大限制后用户被锁定
								status = GuiConstType.Status.LOCK;
								result.setErrorMsg("密码不正确，已经尝试超过" + GuiConstType.Status.ATTEMPT_MAX_TIMES + "，账号已被锁定");
							} else {
								int count = GuiConstType.Status.ATTEMPT_MAX_TIMES - status;
								result.setErrorMsg("密码不正确，再尝试" + count + "次账号将被锁定");
							}
						}
					}
					user.setStatus(status);	// 记录错误密码尝试次数
					result.setErrorCode(GuiConstType.ErrorCode.PASSWD_ERR);
				}
				userDao.update(user);
			} else {
				result.setErrorCode(GuiConstType.ErrorCode.USER_NOT_EXIST);
				result.setErrorMsg("用户不存在");
			}
		} catch (Exception e) {
			result.setErrorCode(GuiConstType.ErrorCode.USER_NOT_EXIST);
			result.setErrorMsg("服务错误");
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * 退出登录
	 * @param userId
	 * @return
	 */
	@Override
	public boolean logout(long userId) {
		return false;
	}
	
	/**
	 * 从session中获得登陆的用户数
	 * @return
	 */
	@Override
	public int getLoinUserCount() {
		int result = 0;
		
		
		
		return result;
	}
	
	/**
	 * 注册
	 * @return
	 */
	@Override
	public EntityCfgUser register(EntityCfgUser newUser) {
		try {
			newUser.setStatus(GuiConstType.Status.RESET_PASSWD);
			newUser.setCreateTime(new Date());
			newUser = userDao.save(newUser);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return newUser;
	}
	/**
	 * 销户（注意不是退出登录）
	 * @return
	 */
	@Override
	public boolean unregister(long userId) {
		boolean isSuccess = false;
		
		try {
			EntityCfgUser user = userDao.queryEntity(userId);
			user.setStatus(GuiConstType.Status.DELETE);
			userDao.update(user);
			isSuccess = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return isSuccess;
	}
	
	
	/**
	 * 获取数据库注册的用户名
	 * @return
	 */
	@Override
	public DataContainer<EntityCfgUser> queryUser(PageLoadConfig pageConfig) {
		DataContainer<EntityCfgUser> result = new DataContainer<>(pageConfig);
		
		try {
			String hqlWhere = EntityCfgUser.P_Status + "!=:status";
			result = userDao.queryEntities(hqlWhere, new String[]{"status"},
					new Object[]{GuiConstType.Status.DELETE}, pageConfig);
		} catch (Exception e) {
			result.setErrorCode(GuiConstType.ErrorCode.SERVICE_ERR);
			result.setErrorMsg("服务错误");
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}

	
	/**
	 * 重置密码
	 * @param userId
	 * @return
	 */
	@Override
	public boolean resetPassword(long userId) {
		boolean isSuccess = false;
		
		try {
			EntityCfgUser user = userDao.queryEntity(userId);
			user.setStatus(GuiConstType.Status.RESET_PASSWD);
			userDao.update(user);
			isSuccess = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return isSuccess;
	}
	
	/**
	 * 锁住用户，不能登陆
	 * @param userId
	 * @return
	 */
	@Override
	public boolean lockUser(long userId) {
		boolean isSuccess = false;
		
		try {
			EntityCfgUser user = userDao.queryEntity(userId);
			user.setStatus(GuiConstType.Status.LOCK);
			userDao.update(user);
			isSuccess = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return isSuccess;
	}

	/**
	 * 解锁用户，可以登陆
	 * @param userId
	 * @return
	 */
	@Override
	public boolean unLockUser(long userId) {
		boolean isSuccess = false;
		
		try {
			EntityCfgUser user = userDao.queryEntity(userId);
			user.setStatus(GuiConstType.Status.OK);
			userDao.update(user);
			isSuccess = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return isSuccess;
	}
}
