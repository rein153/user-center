package com.zsw.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsw.usercenter.common.BaseResponse;
import com.zsw.usercenter.common.ErrorCode;
import com.zsw.usercenter.common.ResultUtils;
import com.zsw.usercenter.constant.UserConstant;
import com.zsw.usercenter.exception.BusinessException;
import com.zsw.usercenter.model.domain.User;
import com.zsw.usercenter.model.domain.request.UserLoginRequest;
import com.zsw.usercenter.model.domain.request.UserRegisterRequest;
import com.zsw.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zsw.usercenter.constant.UserConstant.ADMIN_ROLE;


/**
 * 用户接口
 *
 * @author zsw
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){

        if(userRegisterRequest == null){
//            return null;
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String plantCode = userRegisterRequest.getPlantCode();
        if(StringUtils.isAllBlank(userAccount,userPassword,checkPassword,plantCode)){
            return null;
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword, plantCode);
//        return userService.userRegister(userAccount, userPassword, checkPassword, plantCode);
//        return new BaseResponse<>(0,result,"ok");
        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){

        if(userLoginRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();


        if(StringUtils.isAllBlank(userAccount,userPassword)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        User user = userService.userLogin(userAccount, userPassword, request);
//        return userService.userLogin(userAccount, userPassword, request);
//        return new BaseResponse<>(0,user,"ok");
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
//        return userService.userLogout(request);
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request){
        // 用户角色鉴权,仅管理员可查询
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }

        // 全部返回信息太多了，过滤一下
//        return userService.list(queryWrapper);

        List<User> userList = userService.list(queryWrapper);
        // java8 数据流 遍历每个元素 密码都设置为空 最后还原成list
//        return userList.stream().map(user -> {
//            user.setUserPassword(null);
//            return userService.getSafetyUser(user);
//        }).collect(Collectors.toList());
        List<User> list = userList.stream().map(user -> {
            user.setUserPassword(null);
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUsers(@RequestBody long id, HttpServletRequest request){
        // 用户角色鉴权,仅管理员可删除
        if(!isAdmin(request)){
//            return false;
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if(id <= 0) {
//            return false;
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

//        return userService.removeById(id);
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */

    //查询和删除是同样的逻辑
    private boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        if(user == null || user.getUserRole() != ADMIN_ROLE){
            return false;
        }
        return true;
    }
}
