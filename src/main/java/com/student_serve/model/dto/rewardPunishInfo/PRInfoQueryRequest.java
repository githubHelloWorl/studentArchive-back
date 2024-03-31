package com.student_serve.model.dto.rewardPunishInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class PRInfoQueryRequest implements Serializable {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 状态
     */
    private String state;

    /**
     * 班级
     */
    private String classes;

    /**
     * 类型
     */
    private String fileInfo;

    /**
     * 学期
     */
    private String stime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}