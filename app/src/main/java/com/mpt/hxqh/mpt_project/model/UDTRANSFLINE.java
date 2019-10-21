package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 资产移动行
 */

public class UDTRANSFLINE implements Serializable {
    public String ASSETNUM;//ASSETNUM
    public String CREATEBY;//CREATEBY
    public String CREATED;//CREATED
    public String FROMSITE;//FROMSITE
    public String TOSITE;//TOSITE
    public String SERIALNUM;
    public String UDREMARK;
    public String SCANSN;
    public String SECSCAN;
    public String UDTRANSFLINEID;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"UDREMARK\":\"")
                .append(UDREMARK == null? "" : UDREMARK).append('\"');
        sb.append(",\"SCANSN\":\"")
                .append(SCANSN==null?"":SCANSN).append('\"');
        sb.append(",\"SECSCAN\":\"")
                .append(SECSCAN==null?"":SECSCAN).append('\"');
        sb.append(",\"UDTRANSFLINEID\":\"")
                .append(UDTRANSFLINEID).append('\"');
        sb.append(",\"relationShip\":[{\"\":\"\"}]");
        sb.append('}');
        return sb.toString();
    }

    public String getUDTRANSFLINEID() {
        return UDTRANSFLINEID;
    }

    public void setUDTRANSFLINEID(String UDTRANSFLINEID) {
        if (UDTRANSFLINEID!=null && UDTRANSFLINEID.contains(",")){
            UDTRANSFLINEID = UDTRANSFLINEID.replace(",","");
        }
        this.UDTRANSFLINEID = UDTRANSFLINEID;
    }

    public String getSCANSN() {
        return SCANSN;
    }

    public void setSCANSN(String SCANSN) {
        this.SCANSN = SCANSN;
    }

    public String getSECSCAN() {
        return SECSCAN;
    }

    public void setSECSCAN(String SECSCAN) {
        this.SECSCAN = SECSCAN;
    }

    public String getUDREMARK() {
        return UDREMARK;
    }

    public void setUDREMARK(String UDREMARK) {
        this.UDREMARK = UDREMARK;
    }

    public String getSERIALNUM() {
        return SERIALNUM;
    }

    public void setSERIALNUM(String SERIALNUM) {
        this.SERIALNUM = SERIALNUM;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getCREATEBY() {
        return CREATEBY;
    }

    public void setCREATEBY(String CREATEBY) {
        this.CREATEBY = CREATEBY;
    }

    public String getCREATED() {
        return CREATED;
    }

    public void setCREATED(String CREATED) {
        this.CREATED = CREATED;
    }

    public String getFROMSITE() {
        return FROMSITE;
    }

    public void setFROMSITE(String FROMSITE) {
        this.FROMSITE = FROMSITE;
    }

    public String getTOSITE() {
        return TOSITE;
    }

    public void setTOSITE(String TOSITE) {
        this.TOSITE = TOSITE;
    }
}
