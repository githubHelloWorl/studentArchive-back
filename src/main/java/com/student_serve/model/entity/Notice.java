package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 公告表
 * @TableName notice
 */
@TableName(value ="notice")
@Data
public class Notice implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 发布时间
     */
    private Date noticeTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}