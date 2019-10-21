package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/22.
 * 物料盘点行
 */

public class UDSTOCKTLINE implements Serializable {
    public String STOCKTNUM;//STOCKTNUM
    public String ASSETNUM;//ASSETNUM
    public String ITEMNUM;//ITEMNUM
    public String CATEGORY;//CATEGORY
    public String CONFIGURE;//CONFIGURE
    public String CHECKSERIAL;//CHECKSERIAL
    public String PHASE;//PHASE
    public String REMARK;//UDREMARK
    public String LINE;//LINE
    public String SERIALNUM;//SERIALNUM
    public String ISCHECK;//ISCHECK
    public int ISSCAN;//ISSCAN
    public String UDSTOCKTLINEID;//UDSTOCKTLINEID
    public String QUANTITY ;//QUANTITY
    public String STKRESULT;//STKRESULT
    public String CHECKDATE;//CHECKDATE
    public String QTYINSTK;//QTYINSTK
    public String STOCKTAKER;//STOCKTAKER

    public void setSTOCKTAKER(String STOCKTAKER) {
        this.STOCKTAKER = STOCKTAKER;
    }
    public String getSTOCKTAKER() {
        return STOCKTAKER;
    }

    public String getQTYINSTK() {
        return QTYINSTK;
    }

    public void setQTYINSTK(String QTYINSTK) {
        this.QTYINSTK = QTYINSTK;
    }

    public void setCHECKDATE(String CHECKDATE) {
        this.CHECKDATE = CHECKDATE;
    }

    public String getCHECKDATE() {
        return CHECKDATE;
    }

    public void setSTKRESULT(String STKRESULT) {
        this.STKRESULT = STKRESULT;
    }
    public String getSTKRESULT() {
        return STKRESULT;
    }
    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getSTOCKTNUM() {
        return STOCKTNUM;
    }

    public void setSTOCKTNUM(String STOCKTNUM) {
        this.STOCKTNUM = STOCKTNUM;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getCONFIGURE() {
        return CONFIGURE;
    }

    public void setCONFIGURE(String CONFIGURE) {
        this.CONFIGURE = CONFIGURE;
    }

    public String getCHECKSERIAL() {
        return CHECKSERIAL;
    }

    public void setCHECKSERIAL(String CHECKSERIAL) {
        this.CHECKSERIAL = CHECKSERIAL;
    }

    public String getPHASE() {
        return PHASE;
    }

    public void setPHASE(String PHASE) {
        this.PHASE = PHASE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getLINE() {
        return LINE;
    }

    public void setLINE(String LINE) {
        this.LINE = LINE;
    }

    public String getSERIALNUM() {
        return SERIALNUM;
    }

    public void setSERIALNUM(String SERIALNUM) {
        this.SERIALNUM = SERIALNUM;
    }

    public String getISCHECK() {
        return ISCHECK;
    }

    public void setISCHECK(String ISCHECK) {
        this.ISCHECK = ISCHECK;
    }

    public int getISSCAN() {
        return ISSCAN;
    }

    public void setISSCAN(int ISSCAN) {
        this.ISSCAN = ISSCAN;
    }

    public String getUDSTOCKTLINEID() {
        return UDSTOCKTLINEID;
    }

    public void setUDSTOCKTLINEID(String UDSTOCKTLINEID) {
        this.UDSTOCKTLINEID = UDSTOCKTLINEID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UDSTOCKTLINE that = (UDSTOCKTLINE) o;

        if (ISSCAN != that.ISSCAN) return false;
        if (STOCKTNUM != null ? !STOCKTNUM.equals(that.STOCKTNUM) : that.STOCKTNUM != null)
            return false;
        if (ASSETNUM != null ? !ASSETNUM.equals(that.ASSETNUM) : that.ASSETNUM != null)
            return false;
        if (ITEMNUM != null ? !ITEMNUM.equals(that.ITEMNUM) : that.ITEMNUM != null) return false;
        if (CATEGORY != null ? !CATEGORY.equals(that.CATEGORY) : that.CATEGORY != null)
            return false;
        if (CONFIGURE != null ? !CONFIGURE.equals(that.CONFIGURE) : that.CONFIGURE != null)
            return false;
        if (CHECKSERIAL != null ? !CHECKSERIAL.equals(that.CHECKSERIAL) : that.CHECKSERIAL != null)
            return false;
        if (PHASE != null ? !PHASE.equals(that.PHASE) : that.PHASE != null) return false;
        if (REMARK != null ? !REMARK.equals(that.REMARK) : that.REMARK != null) return false;
        if (LINE != null ? !LINE.equals(that.LINE) : that.LINE != null) return false;
        if (SERIALNUM != null ? !SERIALNUM.equals(that.SERIALNUM) : that.SERIALNUM != null)
            return false;
        if (ISCHECK != null ? !ISCHECK.equals(that.ISCHECK) : that.ISCHECK != null) return false;
        if (UDSTOCKTLINEID != null ? !UDSTOCKTLINEID.equals(that.UDSTOCKTLINEID) : that.UDSTOCKTLINEID != null)
            return false;
        if (QUANTITY != null ? !QUANTITY.equals(that.QUANTITY) : that.QUANTITY != null)
            return false;
        if (STKRESULT != null ? !STKRESULT.equals(that.STKRESULT) : that.STKRESULT != null)
            return false;
        if (CHECKDATE != null ? !CHECKDATE.equals(that.CHECKDATE) : that.CHECKDATE != null)
            return false;
        if (QTYINSTK != null ? !QTYINSTK.equals(that.QTYINSTK) : that.QTYINSTK != null)
            return false;
        return STOCKTAKER != null ? STOCKTAKER.equals(that.STOCKTAKER) : that.STOCKTAKER == null;
    }

    @Override
    public int hashCode() {
        int result = STOCKTNUM != null ? STOCKTNUM.hashCode() : 0;
        result = 31 * result + (ASSETNUM != null ? ASSETNUM.hashCode() : 0);
        result = 31 * result + (ITEMNUM != null ? ITEMNUM.hashCode() : 0);
        result = 31 * result + (CATEGORY != null ? CATEGORY.hashCode() : 0);
        result = 31 * result + (CONFIGURE != null ? CONFIGURE.hashCode() : 0);
        result = 31 * result + (CHECKSERIAL != null ? CHECKSERIAL.hashCode() : 0);
        result = 31 * result + (PHASE != null ? PHASE.hashCode() : 0);
        result = 31 * result + (REMARK != null ? REMARK.hashCode() : 0);
        result = 31 * result + (LINE != null ? LINE.hashCode() : 0);
        result = 31 * result + (SERIALNUM != null ? SERIALNUM.hashCode() : 0);
        result = 31 * result + (ISCHECK != null ? ISCHECK.hashCode() : 0);
        result = 31 * result + ISSCAN;
        result = 31 * result + (UDSTOCKTLINEID != null ? UDSTOCKTLINEID.hashCode() : 0);
        result = 31 * result + (QUANTITY != null ? QUANTITY.hashCode() : 0);
        result = 31 * result + (STKRESULT != null ? STKRESULT.hashCode() : 0);
        result = 31 * result + (CHECKDATE != null ? CHECKDATE.hashCode() : 0);
        result = 31 * result + (QTYINSTK != null ? QTYINSTK.hashCode() : 0);
        result = 31 * result + (STOCKTAKER != null ? STOCKTAKER.hashCode() : 0);
        return result;
    }
}
