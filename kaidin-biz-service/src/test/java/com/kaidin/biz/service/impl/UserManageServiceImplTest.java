package com.kaidin.biz.service.impl;

import com.kaidin.biz.service.IUserManageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 测试类
 *
 * @author xiaobin
 * @date 2020-09-13 20:53
 */
@Transactional
@ContextConfiguration({"classpath*:spring/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserManageServiceImplTest {
    @Resource
    private IUserManageService userManageService;

//    @Test
    public void testLogin() {
        userManageService.login("123", "abc");
    }
}