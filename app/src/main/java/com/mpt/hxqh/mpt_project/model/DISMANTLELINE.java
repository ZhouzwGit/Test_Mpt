package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by zzw on 2019/9/25.
 */

public class DISMANTLELINE implements Serializable {

    private String ASSETNUM;
    private String DESCRIPTION;
    private String DISMANTLEDATE;
    private String LINE;
    private String QUANTITY;
    private String SERIAL;
    private String UDREMARK;
    private String UDTOLOC;
    private String UDORDERNUM;

    public String getUDORDERNUM() {
        return UDORDERNUM;
    }

    public void setUDORDERNUM(String UDORDERNUM) {
        this.UDORDERNUM = UDORDERNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDISMANTLEDATE(String DISMANTLEDATE) {
        this.DISMANTLEDATE = DISMANTLEDATE;
    }

    public String getDISMANTLEDATE() {
        return DISMANTLEDATE;
    }

    public void setLINE(String LINE) {
        this.LINE = LINE;
    }

    public String getLINE() {
        return LINE;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setUDREMARK(String UDREMARK) {
        this.UDREMARK = UDREMARK;
    }

    public String getUDREMARK() {
        return UDREMARK;
    }

    public void setUDTOLOC(String UDTOLOC) {
        this.UDTOLOC = UDTOLOC;
    }

    public String getUDTOLOC() {
        return UDTOLOC;
    }
}
