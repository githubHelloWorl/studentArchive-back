package com.student_serve.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 奖惩信息表
 * @TableName punish
 */
@TableName(value ="punish")
@Data
public class Punish implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 证件编号
     */
    private String fileid;

    /**
     * 处分原因
     */
    private String filereason;

    /**
     * 处分类别
     */
    private String type;

    /**
     * 处分级别
     */
    private String level;

    /**
     * 处分周期
     */
    private String circle;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Punish other = (Punish) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFileid() == null ? other.getFileid() == null : this.getFileid().equals(other.getFileid()))
            && (this.getFilereason() == null ? other.getFilereason() == null : this.getFilereason().equals(other.getFilereason()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getCircle() == null ? other.getCircle() == null : this.getCircle().equals(other.getCircle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFileid() == null) ? 0 : getFileid().hashCode());
        result = prime * result + ((getFilereason() == null) ? 0 : getFilereason().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getCircle() == null) ? 0 : getCircle().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileid=").append(fileid);
        sb.append(", filereason=").append(filereason);
        sb.append(", type=").append(type);
        sb.append(", level=").append(level);
        sb.append(", circle=").append(circle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}