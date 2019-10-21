package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 公司
 */

public class COMPANIES implements Serializable {
    public String COMPANY;//company
    public String NAME;//name
    public String ORGID;//orgid

    public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getORGID() {
        return ORGID;
    }

    public void setORGID(String ORGID) {
        this.ORGID = ORGID;
    }
}
