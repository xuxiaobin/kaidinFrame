package com.kaidin.gui.service.interfaces;

import java.util.List;

import com.kaidin.gui.model.BoMenu;
/**
 * �˵������Ľӿ�
 * @author xuxiaobin	kaidin@foxmail.com
 *
 */
public interface IMenuManageService {
	/**
	 * ��ȡ�û��Ĳ˵�
	 * @param userId
	 * @return
	 */
	List<BoMenu> getMenu(long userId);
}
