package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 物料盘点
 */

public class UDSTOCKT implements Serializable {
    public String UDSTOCKTID;
    public String STOCKTNUM;//STOCKTNUM
    public String DESCRIPTION;//DESCRIPTION.
    public String STATUS;//STATUS.
    public String LOCATION;//LOCATION
    public String VENDOR;//VENDOR

    public String getUDSTOCKTID() {
        return UDSTOCKTID;
    }

    public void setUDSTOCKTID(String UDSTOCKTID) {
        this.UDSTOCKTID = UDSTOCKTID;
    }

    public String getSTOCKTNUM() {
        return STOCKTNUM;
    }

    public void setSTOCKTNUM(String STOCKTNUM) {
        this.STOCKTNUM = STOCKTNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getVENDOR() {
        return VENDOR;
    }

    public void setVENDOR(String VENDOR) {
        this.VENDOR = VENDOR;
    }
}
