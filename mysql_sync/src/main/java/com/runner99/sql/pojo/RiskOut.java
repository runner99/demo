package com.runner99.sql.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName risk_out
 */
@TableName(value ="risk_out")
@Data
public class RiskOut implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "out_type")
    private String outType;

    /**
     * 
     */
    @TableField(value = "out_ip")
    private String outIp;

    /**
     * 
     */
    @TableField(value = "out_port")
    private String outPort;

    /**
     * 
     */
    @TableField(value = "out_protocol")
    private String outProtocol;

    /**
     * 
     */
    @TableField(value = "enable")
    private Integer enable;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 
     */
    @TableField(value = "out_template_id")
    private Integer outTemplateId;

    /**
     * 
     */
    @TableField(value = "cron")
    private String cron;

    /**
     * 
     */
    @TableField(value = "data_range_start")
    private Integer dataRangeStart;

    /**
     * 
     */
    @TableField(value = "data_range_end")
    private Integer dataRangeEnd;

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
        RiskOut other = (RiskOut) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getOutType() == null ? other.getOutType() == null : this.getOutType().equals(other.getOutType()))
            && (this.getOutIp() == null ? other.getOutIp() == null : this.getOutIp().equals(other.getOutIp()))
            && (this.getOutPort() == null ? other.getOutPort() == null : this.getOutPort().equals(other.getOutPort()))
            && (this.getOutProtocol() == null ? other.getOutProtocol() == null : this.getOutProtocol().equals(other.getOutProtocol()))
            && (this.getEnable() == null ? other.getEnable() == null : this.getEnable().equals(other.getEnable()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getOutTemplateId() == null ? other.getOutTemplateId() == null : this.getOutTemplateId().equals(other.getOutTemplateId()))
            && (this.getCron() == null ? other.getCron() == null : this.getCron().equals(other.getCron()))
            && (this.getDataRangeStart() == null ? other.getDataRangeStart() == null : this.getDataRangeStart().equals(other.getDataRangeStart()))
            && (this.getDataRangeEnd() == null ? other.getDataRangeEnd() == null : this.getDataRangeEnd().equals(other.getDataRangeEnd()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getOutType() == null) ? 0 : getOutType().hashCode());
        result = prime * result + ((getOutIp() == null) ? 0 : getOutIp().hashCode());
        result = prime * result + ((getOutPort() == null) ? 0 : getOutPort().hashCode());
        result = prime * result + ((getOutProtocol() == null) ? 0 : getOutProtocol().hashCode());
        result = prime * result + ((getEnable() == null) ? 0 : getEnable().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getOutTemplateId() == null) ? 0 : getOutTemplateId().hashCode());
        result = prime * result + ((getCron() == null) ? 0 : getCron().hashCode());
        result = prime * result + ((getDataRangeStart() == null) ? 0 : getDataRangeStart().hashCode());
        result = prime * result + ((getDataRangeEnd() == null) ? 0 : getDataRangeEnd().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", outType=").append(outType);
        sb.append(", outIp=").append(outIp);
        sb.append(", outPort=").append(outPort);
        sb.append(", outProtocol=").append(outProtocol);
        sb.append(", enable=").append(enable);
        sb.append(", createtime=").append(createTime);
        sb.append(", updatetime=").append(updateTime);
        sb.append(", outTemplateId=").append(outTemplateId);
        sb.append(", cron=").append(cron);
        sb.append(", dataRangeStart=").append(dataRangeStart);
        sb.append(", dataRangeEnd=").append(dataRangeEnd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}