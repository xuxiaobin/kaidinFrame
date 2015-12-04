package com.kaidin.gui.service.interfaces;

import java.util.List;

import com.kaidin.gui.model.BoMenu;
/**
 * 菜单操作的接口
 * @author xuxiaobin	kaidin@foxmail.com
 *
 */
public interface IMenuManageService {
	/**
	 * 获取用户的菜单
	 * @param userId
	 * @return
	 */
	List<BoMenu> getMenu(long userId);
}
