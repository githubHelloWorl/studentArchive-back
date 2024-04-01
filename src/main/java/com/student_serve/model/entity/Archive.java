package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 档案
 * @TableName archive
 */
@TableName(value ="archive")
@Data
public class Archive implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学号
     */
    private String sid;

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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}