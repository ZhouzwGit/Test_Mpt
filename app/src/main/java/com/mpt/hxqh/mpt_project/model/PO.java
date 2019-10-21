package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 */

public class PO implements Serializable {
    public String PONUM;//Order
    public String DESCRIPTION;//Desc.
    public String REVISIONNUM;//Revision
    public String STATUS;//Status

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getPONUM() {
        return PONUM;
    }

    public void setPONUM(String PONUM) {
        this.PONUM = PONUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getREVISIONNUM() {
        return REVISIONNUM;
    }

    public void setREVISIONNUM(String REVISIONNUM) {
        this.REVISIONNUM = REVISIONNUM;
    }
}
