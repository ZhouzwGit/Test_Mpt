package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * ITEM表的选择
 */

public class INVENTORY implements Serializable {
    public String ITEMNUM;//ITEMNUM
    public String LOCATION;//LOCATION
    public String ITEMDESC;//ITEMDESC
    public String STOREROOM_NAME;//STOREROOM_NAME
    public String SITEID;//SITEID

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getITEMDESC() {
        return ITEMDESC;
    }

    public void setITEMDESC(String ITEMDESC) {
        this.ITEMDESC = ITEMDESC;
    }

    public String getSTOREROOM_NAME() {
        return STOREROOM_NAME;
    }

    public void setSTOREROOM_NAME(String STOREROOM_NAME) {
        this.STOREROOM_NAME = STOREROOM_NAME;
    }

    public String getSITEID() {
        return SITEID;
    }

    public void setSITEID(String SITEID) {
        this.SITEID = SITEID;
    }
}
