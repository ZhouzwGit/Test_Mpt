package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 资产报废
 */

public class UDRETIRELINE implements Serializable {
    public String ASSETNUM;//ASSETNUM
    public String FROMLOC;//FROMLOC
    public String LINE;//LINE
    public String RETIREDATE;//RETIREDATE
    public String RETIRELOC;//RETIRELOC
    public String RETIRENUM;//RETIRENUM
    public String SERIAL;//SERIAL
    public String SCANSN;
    public String UDRETIRELINEID;
    public String UDREMARK;
    public String SECSCAN;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"ASSETNUM\":\"")
                .append(ASSETNUM==null?"":ASSETNUM).append('\"');
        sb.append(",\"FROMLOC\":\"")
                .append(FROMLOC==null?"":FROMLOC).append('\"');
        sb.append(",\"LINE\":\"")
                .append(LINE==null?"":LINE).append('\"');
        sb.append(",\"RETIRELOC\":\"")
                .append(RETIRELOC==null?"":RETIRELOC).append('\"');
        sb.append(",\"RETIRENUM\":\"")
                .append(RETIRENUM==null?"":RETIRENUM).append('\"');
        sb.append(",\"SERIAL\":\"")
                .append(SERIAL==null?"":SERIAL).append('\"');
        sb.append(",\"SCANSN\":\"")
                .append(SCANSN==null?"":SCANSN).append('\"');
        sb.append(",\"UDREMARK\":\"")
                .append(UDREMARK==null?"":UDREMARK).append('\"');
        sb.append(",\"SECSCAN\":\"")
                .append(SECSCAN==null?"":SECSCAN).append('\"');
        sb.append(",\"relationShip\":[{\"\":\"\"}]");
        sb.append('}');
        return sb.toString();
    }

    public String getUDRETIRELINEID() {
        return UDRETIRELINEID;
    }

    public void setUDRETIRELINEID(String UDRETIRELINEID) {
        if (UDRETIRELINEID!=null&&UDRETIRELINEID.contains(",")){
            UDRETIRELINEID = UDRETIRELINEID.replace(",","");
        }
        this.UDRETIRELINEID = UDRETIRELINEID;
    }

    public String getSCANSN() {
        return SCANSN;
    }

    public void setSCANSN(String SCANSN) {
        this.SCANSN = SCANSN;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getFROMLOC() {
        return FROMLOC;
    }

    public void setFROMLOC(String FROMLOC) {
        this.FROMLOC = FROMLOC;
    }

    public String getLINE() {
        return LINE;
    }

    public void setLINE(String LINE) {
        this.LINE = LINE;
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

    public String getRETIRENUM() {
        return RETIRENUM;
    }

    public void setRETIRENUM(String RETIRENUM) {
        this.RETIRENUM = RETIRENUM;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

}
