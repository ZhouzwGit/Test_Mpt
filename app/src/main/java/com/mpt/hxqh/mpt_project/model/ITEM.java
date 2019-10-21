package com.mpt.hxqh.mpt_project.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * 位置
 */

public class ITEM implements Serializable {
    public String ITEMNUM;//ITEMNUM
    public String DESCRIPTION;//DESCRIPTION
    public String COMMODITYGROUP;//COMMODITYGROUP
    public String COMMODITY;//COMMODITY

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getCOMMODITYGROUP() {
        return COMMODITYGROUP;
    }

    public void setCOMMODITYGROUP(String COMMODITYGROUP) {
        this.COMMODITYGROUP = COMMODITYGROUP;
    }

    public String getCOMMODITY() {
        return COMMODITY;
    }

    public void setCOMMODITY(String COMMODITY) {
        this.COMMODITY = COMMODITY;
    }
}
