package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 资产维修
 */

public class UDASST implements Serializable {
    public String UDASSTID;//UDASSTID
    public String DESCRIPTION;//DESCRIPTION
    public String LOCATION;//LOCATION
    public String REPAIRNUM;//REPAIRNUM
    public String UDASSTNUM;//UDASSTNUM
    public String UDREPDATE;//UDREPDATE
    public String UDSTATUS;//UDSTATUS

    public String getUDASSTID() {
        return UDASSTID;
    }

    public void setUDASSTID(String UDASSTID) {
        this.UDASSTID = UDASSTID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getUDASSTNUM() {
        return UDASSTNUM;
    }

    public void setUDASSTNUM(String UDASSTNUM) {
        this.UDASSTNUM = UDASSTNUM;
    }

    public String getUDREPDATE() {
        return UDREPDATE;
    }

    public void setUDREPDATE(String UDREPDATE) {
        this.UDREPDATE = UDREPDATE;
    }

    public String getUDSTATUS() {
        return UDSTATUS;
    }

    public void setUDSTATUS(String UDSTATUS) {
        this.UDSTATUS = UDSTATUS;
    }

    public String getREPAIRNUM() {
        return REPAIRNUM;
    }

    public void setREPAIRNUM(String REPAIRNUM) {
        this.REPAIRNUM = REPAIRNUM;
    }
}
