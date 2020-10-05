package com.kaidin.biz.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import com.kaidin.biz.common.constant.CommonErrEnum;
import com.kaidin.biz.common.constant.UserStatusEnum;
import com.kaidin.biz.service.IUserManageService;
import com.kaidin.appframe.service.interceptor.PageDataLog;
import com.kaidin.common.util.constant.ConstType;
import com.kaidin.common.util.encrypt.EncryptUtil;
import com.kaidin.common.util.exception.AssertUtil;
import com.kaidin.common.util.exception.BaseException;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageDataBuilder;
import com.kaidin.common.util.query.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaidin.db.dao.IEntityCfgUserDao;
import com.kaidin.db.entity.EntityCfgUser;

/**
 * 用户管理服务类
 *
 * @author xuxiaobin kaidin@foxmail.com
 */
@Service
public class UserManageServiceImpl implements IUserManageService {
    private static final transient Logger LOGGER = LoggerFactory.getLogger(UserManageServiceImpl.class);
    /** 用户尝试登陆次数，超过该次数用户会被锁定 */
    private static int ATTEMPT_MAX_TIMES = 3;
    /** 用户锁定之后再登陆的冷却时间 */
    private static long ATTEMPT_INTERVAL_TIME = 3 * ConstType.time.MS_OF_MINUTE;
    @Resource
    private IEntityCfgUserDao userDao;

    /**
     * 根据登陆用户名和密码获取数据库表记录
     *
     * @param loginName
     * @param loginPasswd
     * @return
     */
    @PageDataLog
    @Override
    public PageData<EntityCfgUser> login(String loginName, String loginPasswd) {
		String hqlWhere = EntityCfgUser.P_Name + "=:loginName";
		EntityCfgUser user = userDao.queryEntity(hqlWhere, "loginName", loginName);
		AssertUtil.isNotNull(user, CommonErrEnum.PARAMETER_ILLEGAL, "用户不存在");

		try {
            boolean isPasswdSucc = EncryptUtil.md5(loginPasswd).equals(user.getPassword());
            switch (UserStatusEnum.codeOf(user.getStatus())) {
                case ENABLE:
                    if (isPasswdSucc) {
                        // 登陆正常 记录最后登陆时间
                        user.setLoginAttemptsCount(0);
                        user.setLastLoginTime(new Date());
                        break;
                    }
                    int loginAttemptsCount = user.getLoginAttemptsCount();
                    if (ATTEMPT_MAX_TIMES <= loginAttemptsCount) {
                        // 登陆尝试超过规定次数，账号被锁定
                        user.setStatus(UserStatusEnum.LOCKED.getCode());
                        user.setLoginAttemptsCount(loginAttemptsCount + 1);
                        user.setLoginAttemptsTime(new Date());
                        AssertUtil.isFalse(CommonErrEnum.PARAMETER_ILLEGAL, "密码错误尝试超过" + ATTEMPT_MAX_TIMES + "次，账号已被锁定");
                    } else {
                        user.setLoginAttemptsCount(loginAttemptsCount + 1);
                        user.setLoginAttemptsTime(new Date());
                        int count = ATTEMPT_MAX_TIMES - loginAttemptsCount;
                        AssertUtil.isFalse(CommonErrEnum.PARAMETER_ILLEGAL, "密码错误，再尝试" + count + "次账号将被锁定");
                    }
                    break;
                case LOCKED:
                    // 冷却时间
                    long intervalTime = System.currentTimeMillis() - user.getLoginAttemptsTime().getTime();
                    if (ATTEMPT_INTERVAL_TIME >= intervalTime) {
                        user.setLoginAttemptsCount(user.getLoginAttemptsCount() + 1);
                        if (!"root".equals(loginName)) {
                            // 这里root不能被时间锁定，否则没法解锁了
                            user.setLoginAttemptsTime(new Date());
                        }
                        AssertUtil.isFalse(CommonErrEnum.BIZ_ERROR, "账号已被锁定，请3分钟之后尝试");
                    }
                    if (isPasswdSucc) {
                        // 登陆正常 记录最后登陆时间
                        user.setStatus(UserStatusEnum.ENABLE.getCode());
                        user.setLoginAttemptsCount(0);
                        user.setLastLoginTime(new Date());
                        break;
                    }
                    if (!"root".equals(loginName)) {
                        // 这里root不能被时间锁定，否则没法解锁了
                        user.setLoginAttemptsTime(new Date());
                    }
                    user.setLoginAttemptsCount(user.getLoginAttemptsCount() + 1);
                    AssertUtil.isFalse(CommonErrEnum.BIZ_ERROR, "账号已被锁定，每次尝试请间隔3分钟");
                    break;
                case INIT:
                    AssertUtil.isTrue(isPasswdSucc, CommonErrEnum.PARAMETER_ILLEGAL, "用户名或密码错误");
                    // 登陆正常 记录最后登陆时间
                    user.setLoginAttemptsCount(0);
                    user.setLastLoginTime(new Date());
                    break;
                default:
            }
        } catch (BaseException e) {
		    throw e;
        } finally {
            userDao.update(user);
        }

        return PageDataBuilder.success(user);
    }

    /**
     * 退出登录
     *
     * @param userId
     * @return
     */
    @Override
    public boolean logout(long userId) {

        return false;
    }

    /**
     * 从session中获得登陆的用户数
     *
     * @return
     */
    @Override
    public int getLoinUserCount() {
        int result = 0;

        return result;
    }

    /**
     * 用户注册
     *
     * @return
     */
    @Override
    public EntityCfgUser register(EntityCfgUser newUser) {
        AssertUtil.isNotNull(newUser, CommonErrEnum.PARAMETER_ILLEGAL, "参数不能为空");
        AssertUtil.isNotBlank(newUser.getName(), CommonErrEnum.PARAMETER_ILLEGAL, "用户名不能为空");
        AssertUtil.isNotBlank(newUser.getPassword(), CommonErrEnum.PARAMETER_ILLEGAL, "密码不能为空");

        newUser.setStatus(UserStatusEnum.ENABLE.getCode());
        newUser.setCreateTime(new Date());
        newUser = userDao.save(newUser);

        return newUser;
    }

    /**
     * 销户（注意不是退出登录）
     *
     * @return
     */
    @Override
    public boolean unregister(long userId) {
        boolean isSuccess = false;

        try {
            EntityCfgUser user = userDao.queryEntity(userId);
//            user.setStatus(GuiConstType.Status.DELETE);
            userDao.update(user);
            isSuccess = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return isSuccess;
    }

    @Override
    public PageData<EntityCfgUser> queryUser(PageRequest pageReq) {
        return null;
    }

//    /**
//     * 获取数据库注册的用户名
//     *
//     * @return
//     */
//    @Override
//    public PageData<EntityCfgUser> queryUser(PageLoadConfig pageConfig) {
//        PageData<EntityCfgUser> result = new PageData<>(pageConfig);
//
//        try {
//            String hqlWhere = EntityCfgUser.P_Status + "!=:status";
//            result = userDao.queryEntities(hqlWhere, new String[]{"status"}, new Object[]{GuiConstType.Status.DELETE}, pageConfig);
//        } catch (Exception e) {
//            result.setErrCode(GuiConstType.ErrorCode.SERVICE_ERR);
//            result.setErrMsg("服务错误");
//            logger.error(e.getMessage(), e);
//        }
//
//        return result;
//    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @Override
    public boolean resetPassword(long userId) {
        boolean isSuccess = false;

        try {
            EntityCfgUser user = userDao.queryEntity(userId);
            user.setStatus(UserStatusEnum.INIT.getCode());
            userDao.update(user);
            isSuccess = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return isSuccess;
    }

    /**
     * 锁住用户，不能登陆
     *
     * @param userId
     * @return
     */
    @Override
    public boolean lockUser(long userId) {
        boolean isSuccess = false;

        try {
            EntityCfgUser user = userDao.queryEntity(userId);
            user.setStatus(UserStatusEnum.LOCKED.getCode());
            userDao.update(user);
            isSuccess = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return isSuccess;
    }

    /**
     * 解锁用户，可以登陆
     *
     * @param userId
     * @return
     */
    @Override
    public boolean unLockUser(long userId) {
        boolean isSuccess = false;

        try {
            EntityCfgUser user = userDao.queryEntity(userId);
            user.setStatus(UserStatusEnum.ENABLE.getCode());
            userDao.update(user);
            isSuccess = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return isSuccess;
    }
}
