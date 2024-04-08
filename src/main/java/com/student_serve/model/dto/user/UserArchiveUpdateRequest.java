package com.student_serve.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserArchiveUpdateRequest implements Serializable {
    /**
     * 学号
     */
    private String userAccount;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 身份证号
     */
    private String cardId;

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
     * 档案编号
     */
    private String archiveId;

    /**
     * 性别
     */
    private String sex;

    /**
     * 地址
     */
    private String address;

    /**
     * 健康
     */
    private String health;

    /**
     * 生源地
     */
    private String origin;

    /**
     * 民族
     */
    private String nation;

    /**
     * 出生时间
     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
