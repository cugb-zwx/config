package com.example.demo.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title:
 * Project: WebserviceTest
 * Description:
 * Date: 2019/7/9
 * Copyright: Copyright (c) 2020
 * Company: 北京中科院软件中心有限公司 (SEC)
 *
 * @author zwx
 * @version 1.0
 */
public class SwaggerTestEntity {
    @ApiModelProperty("记录id")
    private Integer id;

    @ApiModelProperty("版本号")
    private Integer version;

    @ApiModelProperty("是否通过 3驳回 4通过")
    private Byte status;

    @ApiModelProperty("兑换积分批次id")
    private Integer exchangeBatchId;

    @ApiModelProperty("审核不通过理由")
    private String auditReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getExchangeBatchId() {
        return exchangeBatchId;
    }

    public void setExchangeBatchId(Integer exchangeBatchId) {
        this.exchangeBatchId = exchangeBatchId;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }
}
