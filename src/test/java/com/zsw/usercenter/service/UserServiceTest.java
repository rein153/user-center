package com.zsw.usercenter.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zsw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){

    }

    @Test
    public void userRegister() {
        String userAccount = "zsw";
        String userPassword = "123456";
        String checkPassword = "123456";
        String plantCode = "1";

        long result = userService.userRegister(userAccount,userPassword,checkPassword,plantCode);
        Assertions.assertEquals(-1,result);

        userAccount = "z";
        result = userService.userRegister(userAccount,userPassword,checkPassword, plantCode);
        Assertions.assertEquals(-1,result);

        userAccount = "zsw";
        userPassword = "123456";
        result = userService.userRegister(userAccount,userPassword,checkPassword, plantCode);
        Assertions.assertEquals(-1,result);

        userAccount = "z sw";
        userPassword = "12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword, plantCode);
        Assertions.assertEquals(-1,result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount,userPassword,checkPassword,plantCode);
        Assertions.assertEquals(-1,result);

        userAccount = "zswzsw";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword,plantCode);
        Assertions.assertEquals(-1,result);

        userAccount = "zsw";
        result = userService.userRegister(userAccount,userPassword,checkPassword,plantCode);
        Assertions.assertTrue(result > 0);
    }
}