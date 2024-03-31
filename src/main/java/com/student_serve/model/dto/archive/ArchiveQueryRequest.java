package com.student_serve.model.dto.archive;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArchiveQueryRequest implements Serializable {
    /**
     * 档案编号
     */
    private String archiveId;

    /**
     * 账号
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
     * 班级
     */
    private String classes;


    private static final long serialVersionUID = 1L;
}
