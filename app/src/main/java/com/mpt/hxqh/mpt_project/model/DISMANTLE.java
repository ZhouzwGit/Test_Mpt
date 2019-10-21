/**
  * Copyright 2019 bejson.com 
  */
package com.mpt.hxqh.mpt_project.model;
import java.io.Serializable;
import java.util.Date;

public class DISMANTLE implements Serializable{

    private String CHANGEBY;
    private String CHANGEDATE;
    private String CREATEBY;
    private String CREATEDATE;
    private String DESCRIPTION;
    private String LOCATION;
    private String SITEID;
    private String STATUS;
    private String UDORDERNUM;
    public void setCHANGEBY(String CHANGEBY) {
         this.CHANGEBY = CHANGEBY;
     }
     public String getCHANGEBY() {
         return CHANGEBY;
     }

    public void setCHANGEDATE(String CHANGEDATE) {
         this.CHANGEDATE = CHANGEDATE;
     }
     public String getCHANGEDATE() {
         return CHANGEDATE;
     }

    public void setCREATEBY(String CREATEBY) {
         this.CREATEBY = CREATEBY;
     }
     public String getCREATEBY() {
         return CREATEBY;
     }

    public void setCREATEDATE(String CREATEDATE) {
         this.CREATEDATE = CREATEDATE;
     }
     public String getCREATEDATE() {
         return CREATEDATE;
     }

    public void setDESCRIPTION(String DESCRIPTION) {
         this.DESCRIPTION = DESCRIPTION;
     }
     public String getDESCRIPTION() {
         return DESCRIPTION;
     }

    public void setLOCATION(String LOCATION) {
         this.LOCATION = LOCATION;
     }
     public String getLOCATION() {
         return LOCATION;
     }

    public void setSITEID(String SITEID) {
         this.SITEID = SITEID;
     }
     public String getSITEID() {
         return SITEID;
     }

    public void setSTATUS(String STATUS) {
         this.STATUS = STATUS;
     }
     public String getSTATUS() {
         return STATUS;
     }

    public void setUDORDERNUM(String UDORDERNUM) {
         this.UDORDERNUM = UDORDERNUM;
     }
     public String getUDORDERNUM() {
         return UDORDERNUM;
     }

}