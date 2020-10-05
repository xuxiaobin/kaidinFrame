package com.kaidin.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.kaidin.biz.service.IUserManageService;
import com.kaidin.common.util.query.PageData;
import com.kaidin.db.entity.EntityCfgUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
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

//    @Before
    public void before() {
        EntityCfgUser newUser = new EntityCfgUser();
        newUser.setName("kaidin");
        newUser.setPassword("123456");
        newUser = userManageService.register(newUser);
    }


    @Rollback(false)
//    @Test
    public void testRegister() {
        try {
            EntityCfgUser newUser = new EntityCfgUser();
            newUser.setName("kaidin");
            newUser.setPassword("123456");
            newUser = userManageService.register(newUser);
            System.out.println("\n\n");
            System.out.println("xxxxxxxxxxxxx");
            System.out.println(JSON.toJSONString(newUser));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(JSON.toJSONString(e));
        }
        System.out.println("\n\n");
    }

//    @Test
    public void testLogin() {
        // 123456的md5=e10adc3949ba59abbe56e057f20f883e
        PageData<EntityCfgUser> pageData = userManageService.login("xiaobin", "123456");
        System.out.println("\n\n");
        System.out.println(JSON.toJSONString(pageData));
        System.out.println("\n\n");
    }

    @Test
    public void test() {
    }
}