package com.student_serve.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdatePassRequest implements Serializable {

    /**
     * 学号
     */
    private String userAccount;

    /**
     * 原密码
     */
    private String userPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String checkRePassword;

    private static final long serialVersionUID = 1L;
}
