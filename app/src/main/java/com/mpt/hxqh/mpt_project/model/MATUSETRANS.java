package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 实际物料
 */

public class MATUSETRANS implements Serializable {
    public String ITEMNUM;//ITEMNUM
    public String POSITIVEQUANTITY;//POSITIVEQUANTITY
    public String STORELOC;//STORELOC
    public String SERIALNUM;
    public String UDREMARK;
    public String SCANSN;
    public String SECSCAN;
    public String MATUSETRANSID;

    public String getSERIALNUM() {
        return SERIALNUM;
    }

    public void setSERIALNUM(String SERIALNUM) {
        this.SERIALNUM = SERIALNUM;
    }

    public String getUDREMARK() {
        return UDREMARK;
    }

    public void setUDREMARK(String UDREMARK) {
        this.UDREMARK = UDREMARK;
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

    public String getMATUSETRANSID() {
        return MATUSETRANSID;
    }

    public void setMATUSETRANSID(String MATUSETRANSID) {
        this.MATUSETRANSID = MATUSETRANSID;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getPOSITIVEQUANTITY() {
        return POSITIVEQUANTITY;
    }

    public void setPOSITIVEQUANTITY(String POSITIVEQUANTITY) {
        this.POSITIVEQUANTITY = POSITIVEQUANTITY;
    }

    public String getSTORELOC() {
        return STORELOC;
    }

    public void setSTORELOC(String STORELOC) {
        this.STORELOC = STORELOC;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"UDREMARK\":\"")
                .append(UDREMARK ==null?"": UDREMARK).append('\"');
        sb.append(",\"SCANSN\":\"")
                .append(SCANSN==null?"":SCANSN).append('\"');
        sb.append(",\"SECSCAN\":\"")
                .append(SECSCAN==null?"":SECSCAN).append('\"');
        sb.append(",\"relationShip\":[{\"\":\"\"}]");
        sb.append('}');
        return sb.toString();
    }
}
