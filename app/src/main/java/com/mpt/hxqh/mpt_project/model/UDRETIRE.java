package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 资产报废
 */

public class UDRETIRE implements Serializable {
    public String UDRETIREID;//
    public String RETIRENUM;//RETIRENUM
    public String DESCRIPTION;//DESCRIPTION
    public String RETIREDATE;//RETIREDATE
    public String RETIRELOC;//RETIRELOC
    public String STATUS;//STATUS
    public String LOCATION;//LOCATION

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getUDRETIREID() {
        return UDRETIREID;
    }

    public void setUDRETIREID(String UDRETIREID) {
        this.UDRETIREID = UDRETIREID;
    }

    public String getRETIRENUM() {
        return RETIRENUM;
    }

    public void setRETIRENUM(String RETIRENUM) {
        this.RETIRENUM = RETIRENUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getRETIREDATE() {
        return RETIREDATE;
    }

    public void setRETIREDATE(String RETIREDATE) {
        this.RETIREDATE = RETIREDATE;
    }

    public String getRETIRELOC() {
        return RETIRELOC;
    }

    public void setRETIRELOC(String RETIRELOC) {
        this.RETIRELOC = RETIRELOC;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
