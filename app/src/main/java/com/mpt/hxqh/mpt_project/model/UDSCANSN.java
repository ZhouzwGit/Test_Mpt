package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\1\5 0005.
 */

public class UDSCANSN implements Serializable {
    public String UDSCANSNID;
    public String LINE;
    public String STOCKTNUM;
    public String SERIALNUM;
    public String CREATEBY;

    public String getUDSCANSNID() {
        return UDSCANSNID;
    }

    public void setUDSCANSNID(String UDSCANSNID) {
        this.UDSCANSNID = UDSCANSNID;
    }

    public String getLINE() {
        return LINE;
    }

    public void setLINE(String LINE) {
        this.LINE = LINE;
    }

    public String getSTOCKTNUM() {
        return STOCKTNUM;
    }

    public void setSTOCKTNUM(String STOCKTNUM) {
        this.STOCKTNUM = STOCKTNUM;
    }

    public String getSERIALNUM() {
        return SERIALNUM;
    }

    public void setSERIALNUM(String SERIALNUM) {
        this.SERIALNUM = SERIALNUM;
    }

    public String getCREATEBY() {
        return CREATEBY;
    }

    public void setCREATEBY(String CREATEBY) {
        this.CREATEBY = CREATEBY;
    }
}
