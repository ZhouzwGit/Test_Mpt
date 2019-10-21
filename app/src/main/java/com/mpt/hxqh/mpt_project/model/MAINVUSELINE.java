package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 物料退库
 */

public class MAINVUSELINE implements Serializable {
    public String LINETYPE;//LINETYPE
    public String INVUSENUM;//INVUSENUM
    public String USETYPE;//USETYPE
    public String ITEMNUM;//ITEMNUM
    public String QUANTITY;//QUANTITY
    public String TOLOCATION;//TOLOCATION
    public String TOSTORELOC;//TOSTORELOC
    public String INVUSELINENUM;//INVUSELINENUM

    public String getINVUSELINENUM() {
        return INVUSELINENUM;
    }

    public void setINVUSELINENUM(String INVUSELINENUM) {
        this.INVUSELINENUM = INVUSELINENUM;
    }

    public String getLINETYPE() {
        return LINETYPE;
    }

    public void setLINETYPE(String LINETYPE) {
        this.LINETYPE = LINETYPE;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getTOLOCATION() {
        return TOLOCATION;
    }

    public void setTOLOCATION(String TOLOCATION) {
        this.TOLOCATION = TOLOCATION;
    }

    public String getTOSTORELOC() {
        return TOSTORELOC;
    }

    public void setTOSTORELOC(String TOSTORELOC) {
        this.TOSTORELOC = TOSTORELOC;
    }

    public String getINVUSENUM() {
        return INVUSENUM;
    }

    public void setINVUSENUM(String INVUSENUM) {
        this.INVUSENUM = INVUSENUM;
    }

    public String getUSETYPE() {
        return USETYPE;
    }

    public void setUSETYPE(String USETYPE) {
        this.USETYPE = USETYPE;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }
}
