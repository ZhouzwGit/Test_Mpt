package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 计划物料
 */

public class WPMATERIAL implements Serializable {
    public String ITEMNUM;//ITEMNUM
    public String ITEMQTY;//ITEMQTY
    public String LOCATION;//LOCATION

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getITEMQTY() {
        return ITEMQTY;
    }

    public void setITEMQTY(String ITEMQTY) {
        this.ITEMQTY = ITEMQTY;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }
}
