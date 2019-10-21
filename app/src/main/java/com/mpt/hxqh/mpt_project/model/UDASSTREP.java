package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 资产维修
 */

public class UDASSTREP implements Serializable {
    public String UDASSTNUM;//UDASSTNUM
    public String UDREPDATE;//UDREPDATE
    public String UDREPLOC;//UDREPLOC
    public String UDSERIALNUM;//UDSERIALNUM

    public String getUDASSTNUM() {
        return UDASSTNUM;
    }

    public void setUDASSTNUM(String UDASSTNUM) {
        this.UDASSTNUM = UDASSTNUM;
    }

    public String getUDREPDATE() {
        return UDREPDATE;
    }

    public void setUDREPDATE(String UDREPDATE) {
        this.UDREPDATE = UDREPDATE;
    }

    public String getUDREPLOC() {
        return UDREPLOC;
    }

    public void setUDREPLOC(String UDREPLOC) {
        this.UDREPLOC = UDREPLOC;
    }

    public String getUDSERIALNUM() {
        return UDSERIALNUM;
    }

    public void setUDSERIALNUM(String UDSERIALNUM) {
        this.UDSERIALNUM = UDSERIALNUM;
    }
}
