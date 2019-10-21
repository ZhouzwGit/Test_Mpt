package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 资产移动
 */

public class UDASSETTRANSF implements Serializable {
    public String ASSETTRANNUM;//ASSETTRANNUM
    public String DESCRIPTION;//DESCRIPTION
    public String FROMDEPT;//FROMDEPT
    public String FROMLOC;//FROMLOC
    public String TOSITE;//TOSITE
    public String STATUS;//STATUS

    public String getASSETTRANNUM() {
        return ASSETTRANNUM;
    }

    public void setASSETTRANNUM(String ASSETTRANNUM) {
        this.ASSETTRANNUM = ASSETTRANNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFROMDEPT() {
        return FROMDEPT;
    }

    public void setFROMDEPT(String FROMDEPT) {
        this.FROMDEPT = FROMDEPT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getFROMLOC() {
        return FROMLOC;
    }

    public void setFROMLOC(String FROMLOC) {
        this.FROMLOC = FROMLOC;
    }

    public String getTOSITE() {
        return TOSITE;
    }

    public void setTOSITE(String TOSITE) {
        this.TOSITE = TOSITE;
    }
}
