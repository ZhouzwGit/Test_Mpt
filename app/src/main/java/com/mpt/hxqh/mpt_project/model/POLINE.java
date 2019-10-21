package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 */

public class POLINE implements Serializable {
    public String EXIST;//LineType
    public String LINETYPE;//LINETYPE
    public String LOCATION;//Location
    public String POLINENUM;//POLINENUM
    public String PONUM;//PONUM
    public String SCANNER;//SCANNER
    public String SERIALNUM;//SERIALNUM
    public String STORELOC;//STORELOC
    public String SCANSN;
    public String SECSCAN;
    public String REMARK;
    public String ASSETNUM;
    public String POLINEID;
    public String getPOLINEID() {
        return POLINEID;
    }

    public void setPOLINEID(String POLINEID) {
        if (POLINEID!=null&&POLINEID.contains(",")){
            POLINEID = POLINEID.replace(",","");
        }
        this.POLINEID = POLINEID;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getSECSCAN() {
        return SECSCAN;
    }

    public void setSECSCAN(String SECSCAN) {
        this.SECSCAN = SECSCAN;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getSCANSN() {
        return SCANSN;
    }

    public void setSCANSN(String SCANSN) {
        this.SCANSN = SCANSN;
    }

    public String getEXIST() {
        return EXIST;
    }

    public void setEXIST(String EXIST) {
        this.EXIST = EXIST;
    }

    public String getLINETYPE() {
        return LINETYPE;
    }

    public void setLINETYPE(String LINETYPE) {
        this.LINETYPE = LINETYPE;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getPOLINENUM() {
        return POLINENUM;
    }

    public void setPOLINENUM(String POLINENUM) {
        this.POLINENUM = POLINENUM;
    }

    public String getPONUM() {
        return PONUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }

    public String getSCANNER() {
        return SCANNER;
    }

    public void setSCANNER(String SCANNER) {
        this.SCANNER = SCANNER;
    }

    public String getSERIALNUM() {
        return SERIALNUM;
    }

    public void setSERIALNUM(String SERIALNUM) {
        this.SERIALNUM = SERIALNUM;
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
        sb.append("\"SERIALNUM\":\"")
                .append(SERIALNUM==null?"":SERIALNUM).append('\"');
        sb.append(",\"SCANSN\":\"")
                .append(SCANSN==null?"":SCANSN).append('\"');
        sb.append(",\"SECSCAN\":\"")
                .append(SECSCAN==null?"":SECSCAN).append('\"');
        sb.append(",\"UDREMARK\":\"")
                .append(REMARK==null?"":REMARK).append('\"');
        sb.append(",\"relationShip\":[{\"\":\"\"}]");
        sb.append('}');
        return sb.toString();
    }
}
