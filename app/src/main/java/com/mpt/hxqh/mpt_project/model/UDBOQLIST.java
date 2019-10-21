package com.mpt.hxqh.mpt_project.model;

/**
 * Created by zzw on 2019/1/2.
 */

public class UDBOQLIST {
    public String ASSETNUM;
    public String ITEMNUM;
    public String UDBOQLISTID;
    public String SCANSN;
    public String SECSCAN;
    public String SERIALNUM;
    public String REMARK;
    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }
    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getUDBOQLISTID() {
        return UDBOQLISTID;
    }

    public void setUDBOQLISTID(String UDBOQLISTID) {
        this.UDBOQLISTID = UDBOQLISTID;
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

    public String getSERIALNUM() {
        return SERIALNUM;
    }

    public void setSERIALNUM(String SERIALNUM) {
        this.SERIALNUM = SERIALNUM;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"SCANSN\":\"")
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
