package com.student_serve.service;

import com.student_serve.model.dto.user.UserRegisterRequest;
import com.student_serve.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author yang99977
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-03-25 19:12:41
*/
public interface UserService extends IService<User> {

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 进行登录
     */
    User userLogin(User user,HttpServletRequest request);

    /**
     * 进行注册
     * @param user
     * @return
     */
    User userRegister(UserRegisterRequest user);

    /**
     * 修改密码
     * @param user
     * @return
     */
    User updatePass(UserRegisterRequest user);

    User updateUser(User user);

    /**
     * 查找学号
     * @param classes
     * @return
     */
    List<String> getUserAccountByClasses(String classes);

    /**
     * 查找用户根据角色
     * @param userRole
     * @return
     */
    List<User> getUserByRole(String userRole);

    /**
     * 得到学生
     */
    List<User> queryPostStudent(User user);

    /**
     * 得到用户
     */
    List<User> queryUser(User user);

    User deleteUser(User user);
}
