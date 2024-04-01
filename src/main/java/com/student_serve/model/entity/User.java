package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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

//    /**
//     * 确定密码
//     */
//    private String checkRePassword;

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

    /**
     * 职务
     */
    private String job;

    /**
     * 职称
     */
    private String duty;

    /**
     * 单位
     */
    private String unity;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}