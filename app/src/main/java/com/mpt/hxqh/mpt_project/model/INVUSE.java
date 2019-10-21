package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 资产转移
 */

public class INVUSE implements Serializable {
    public String INVUSEID;//INVUSEID
    public String INVUSENUM;//Order
    public String DESCRIPTION;//描述
    public String FROMSTORELOC;//From Storeroom
    public String INVOWNER;//Inventory Owner
    public String STATUS;//Status

    public String getINVUSEID() {
        return INVUSEID;
    }

    public void setINVUSEID(String INVUSEID) {
        this.INVUSEID = INVUSEID;
    }

    public String getINVUSENUM() {
        return INVUSENUM;
    }

    public void setINVUSENUM(String INVUSENUM) {
        this.INVUSENUM = INVUSENUM;
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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
