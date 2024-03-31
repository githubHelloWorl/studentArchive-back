package com.student_serve.model.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确定密码
     */
    private String checkRePassword;

    /**
     * 身份证号
     */
    private String cardId;

    /**
     * 用户角色: student/teacher/admin/ban
     */
    private String userRole;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 院系
     */
    private String department;

    /**
     * 班级
     */
    private String classes;
    private static final long serialVersionUID = 1L;
}