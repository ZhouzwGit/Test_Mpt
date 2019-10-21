package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 资产
 */

public class ASSET implements Serializable {
    public String ASSETNUM;//Asset Number
    public String ASSETTYPE;//The predefined type of this asset.
    public String DESCRIPTION;//Describes the asset.
    public String LOCATION;//LOCATION
    public String PARENT;//PARENT
    public String SERIALNUM;//SERIALNUM
    public String SITEID;//SITEID
    public String STATUS;//STATUS
    public String ITEMNUM;//ITEMNUM
    public String VENDOR;//VENDOR
    public String INSTALLDATE;//INSTALLDATE
    public String PATDATE;//PATDATE
    public String CONFIGURE;//CONFIGURE
    public String PHASE;//PHASE
    public String CATEGORY;//CATEGORY
    public String TYPE;//CATEGORY



    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }

    public String getASSETTYPE() {
        return ASSETTYPE;
    }

    public void setASSETTYPE(String ASSETTYPE) {
        this.ASSETTYPE = ASSETTYPE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getPARENT() {
        return PARENT;
    }

    public void setPARENT(String PARENT) {
        this.PARENT = PARENT;
    }

    public String getSERIALNUM() {
        return SERIALNUM;
    }

    public void setSERIALNUM(String SERIALNUM) {
        this.SERIALNUM = SERIALNUM;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getVENDOR() {
        return VENDOR;
    }

    public void setVENDOR(String VENDOR) {
        this.VENDOR = VENDOR;
    }

    public String getINSTALLDATE() {
        return INSTALLDATE;
    }

    public void setINSTALLDATE(String INSTALLDATE) {
        this.INSTALLDATE = INSTALLDATE;
    }

    public String getPATDATE() {
        return PATDATE;
    }

    public void setPATDATE(String PATDATE) {
        this.PATDATE = PATDATE;
    }

    public String getCONFIGURE() {
        return CONFIGURE;
    }

    public void setCONFIGURE(String CONFIGURE) {
        this.CONFIGURE = CONFIGURE;
    }

    public String getPHASE() {
        return PHASE;
    }

    public void setPHASE(String PHASE) {
        this.PHASE = PHASE;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
