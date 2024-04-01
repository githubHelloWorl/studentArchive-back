package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 奖惩信息表
 * @TableName rewardpunishinfo
 */
@TableName(value ="rewardpunishinfo")
@Data
public class Rewardpunishinfo implements Serializable {
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
     * 教师工号
     */
    private String tid;

    /**
     * 证件编号
     */
    private String fileId;

    /**
     * 奖惩证书名称
     */
    private String fileName;

    /**
     * 未审核-0/通过-1/不通过-2
     */
    private String state;

    /**
     * 证书信息0-处分/1-证书
     */
    private String fileInfo;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 颁发单位
     */
    private String fileUnit;

    /**
     * 审核教师
     */
    private String tname;

    /**
     * 颁发时间
     */
    private Date fileTime;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 审核时间
     */
    private Date checkTime;

    /**
     * 学年学期
     */
    private String stime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}