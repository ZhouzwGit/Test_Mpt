package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 库存退回弹出框的表
 */

public class GETREFUNDLINE implements Serializable {
    public String ACTUALDATE;//ACTUALDATE
    public String CONDITIONCODE;//CONDITIONCODE
    public String DESCRIPTION;//DESCRIPTION
    public String GLDEBITACCT;//GLDEBITACCT
    public String INVUSENUM;//INVUSENUM
    public String ISSUETO;//ISSUETO
    public String ITEMNUM;//ITEMNUM
    public String ITEMTYPE;//ITEMTYPE
    public String MRNUM;//MRNUM
    public String QTYRETURNED;//QTYRETURNED
    public String QUANTITY;//QUANTITY
    public String REFWO;//REFWO
    public String ROTASSETNUM;//ROTASSETNUM

    public String getACTUALDATE() {
        return ACTUALDATE;
    }

    public void setACTUALDATE(String ACTUALDATE) {
        this.ACTUALDATE = ACTUALDATE;
    }

    public String getCONDITIONCODE() {
        return CONDITIONCODE;
    }

    public void setCONDITIONCODE(String CONDITIONCODE) {
        this.CONDITIONCODE = CONDITIONCODE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getGLDEBITACCT() {
        return GLDEBITACCT;
    }

    public void setGLDEBITACCT(String GLDEBITACCT) {
        this.GLDEBITACCT = GLDEBITACCT;
    }

    public String getINVUSENUM() {
        return INVUSENUM;
    }

    public void setINVUSENUM(String INVUSENUM) {
        this.INVUSENUM = INVUSENUM;
    }

    public String getISSUETO() {
        return ISSUETO;
    }

    public void setISSUETO(String ISSUETO) {
        this.ISSUETO = ISSUETO;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getITEMTYPE() {
        return ITEMTYPE;
    }

    public void setITEMTYPE(String ITEMTYPE) {
        this.ITEMTYPE = ITEMTYPE;
    }

    public String getMRNUM() {
        return MRNUM;
    }

    public void setMRNUM(String MRNUM) {
        this.MRNUM = MRNUM;
    }

    public String getQTYRETURNED() {
        return QTYRETURNED;
    }

    public void setQTYRETURNED(String QTYRETURNED) {
        this.QTYRETURNED = QTYRETURNED;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getREFWO() {
        return REFWO;
    }

    public void setREFWO(String REFWO) {
        this.REFWO = REFWO;
    }

    public String getROTASSETNUM() {
        return ROTASSETNUM;
    }

    public void setROTASSETNUM(String ROTASSETNUM) {
        this.ROTASSETNUM = ROTASSETNUM;
    }
}
