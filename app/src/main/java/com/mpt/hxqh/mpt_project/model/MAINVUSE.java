package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 物料退库
 */

public class MAINVUSE implements Serializable {
    public String INVUSEID;
    public String DESCRIPTION;//DESCRIPTION
    public String FROMSTORELOC;//FROMSTORELOC
    public String INVOWNER;//INVOWNER
    public String INVUSENUM;//INVUSENUM
    public String SITEID;//SITEID
    public String STATUS;//STATUS
    public String USETYPE;//USETYPE

    public String getINVUSEID() {
        return INVUSEID;
    }

    public void setINVUSEID(String INVUSEID) {
        this.INVUSEID = INVUSEID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFROMSTORELOC() {
        return FROMSTORELOC;
    }

    public void setFROMSTORELOC(String FROMSTORELOC) {
        this.FROMSTORELOC = FROMSTORELOC;
    }

    public String getINVOWNER() {
        return INVOWNER;
    }

    public void setINVOWNER(String INVOWNER) {
        this.INVOWNER = INVOWNER;
    }

    public String getINVUSENUM() {
        return INVUSENUM;
    }

    public void setINVUSENUM(String INVUSENUM) {
        this.INVUSENUM = INVUSENUM;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getUSETYPE() {
        return USETYPE;
    }

    public void setUSETYPE(String USETYPE) {
        this.USETYPE = USETYPE;
    }
}
