package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MEASUREUNIT implements Serializable {
    public String MEASUREUNITID;//MEASUREUNITID
    public String ABBREVIATION;//ABBREVIATION
    public String DESCRIPTION;//DESCRIPTION

    public String getMEASUREUNITID() {
        return MEASUREUNITID;
    }

    public void setMEASUREUNITID(String MEASUREUNITID) {
        this.MEASUREUNITID = MEASUREUNITID;
    }

    public String getABBREVIATION() {
        return ABBREVIATION;
    }

    public void setABBREVIATION(String ABBREVIATION) {
        this.ABBREVIATION = ABBREVIATION;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
}
