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
    private String FIRSTSCAN;
    private String SECONDSCAN;
    private String UDREPLACEID;
    private String UDDISMANTLELINEID;

    public String getUDREPLACEID() {
        return UDREPLACEID;
    }

    public void setUDREPLACEID(String UDREPLACEID) {
        this.UDREPLACEID = UDREPLACEID;
    }

    public String getUDDISMANTLELINEID() {
        return UDDISMANTLELINEID;
    }

    public void setUDDISMANTLELINEID(String UDDISMANTLELINEID) {
        this.UDDISMANTLELINEID = UDDISMANTLELINEID;
    }

    public String getFIRSTSCAN() {
        return FIRSTSCAN;
    }

    public void setFIRSTSCAN(String FIRSTSCAN) {
        this.FIRSTSCAN = FIRSTSCAN;
    }

    public String getSECONDSCAN() {
        return SECONDSCAN;
    }

    public void setSECONDSCAN(String SECONDSCAN) {
        this.SECONDSCAN = SECONDSCAN;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"UDREMARK\":\"")
                .append(UDREMARK ==null?"": UDREMARK).append('\"');
        sb.append(",\"FIRSTSCAN\":\"")
                .append(FIRSTSCAN==null?"":FIRSTSCAN).append('\"');
        sb.append(",\"SECONDSCAN\":\"")
                .append(SECONDSCAN==null?"":SECONDSCAN).append('\"');
        sb.append(",\"relationShip\":[{\"\":\"\"}]");
        sb.append('}');
        return sb.toString();
    }
}
