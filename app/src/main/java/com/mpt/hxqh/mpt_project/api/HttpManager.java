package com.mpt.hxqh.mpt_project.api;


import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.application.BaseApplication;
import com.mpt.hxqh.mpt_project.bean.LoginResults;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.GetSSL;

import org.apache.http.Header;
import org.apache.http.conn.ssl.SSLSocketFactory;


/**
 * Created by apple on 15/5/27.
 */
public class HttpManager {

    private static final String TAG = "HttpManager";

    /**
     * 设置位置*
     */
    public static String getLocationUrl(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATIONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'!=INACTIVE','TYPE':'=STOREROOM'}}";
        } else {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATIONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'!=INACTIVE','TYPE':'=STOREROOM'},'sinorsearch':{'LOCATION':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置位置*
     */
    public static String getLocationUrl0(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATIONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'!=INACTIVE'}}";
        } else {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATIONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'!=INACTIVE'},'sinorsearch':{'LOCATION':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置位置*
     */
    public static String getLocation1Url(String vlaue, String type, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATIONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'TYPE':'" + type + "'}}";
        } else {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATIONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'TYPE':'" + type + "'},'sinorsearch':{'LOCATION':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置位置,类型,状态*
     */
    public static String getLocation2Url(String vlaue, String type, String status, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATIONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'TYPE':'" + type + "','STATUS':'" + status + "'}}";
        } else {
            return "{'appid':'" + Constants.LOCATION_APPID + "','objectname':'" + Constants.LOCATIONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'TYPE':'" + type + "','STATUS':'" + status + "'},'sinorsearch':{'LOCATION':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置人员*
     */
    public static String getPersonUrl(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','sinorsearch':{'PERSONID':'" + vlaue + "','DISPLAYNAME':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置INVBALANCES*
     */
    public static String getInvbalancesUrl(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.INVBALANCES_APPID + "','objectname':'" + Constants.INVBALANCES_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.INVBALANCES_APPID + "','objectname':'" + Constants.INVBALANCES_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','sinorsearch':{'BINNUM':'" + vlaue + "','LOTNUM':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置Site*
     */
    public static String getSiteUrl(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.SITE_APPID + "','objectname':'" + Constants.SITE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.SITE_APPID + "','objectname':'" + Constants.SITE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','sinorsearch':{'BINNUM':'" + vlaue + "','LOTNUM':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置ITEM*
     */
    public static String getItemUrl(String vlaue, String storeroom, int curpage, int showcount) {
        if (vlaue.equals("")) {
            if (storeroom == null || storeroom.equals("")) {
                return "{'appid':'" + Constants.ITEM_APPID + "','objectname':'" + Constants.ITEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'=ACTIVE'}}";
            } else {
                return "{'appid':'" + Constants.ITEM_APPID + "','objectname':'" + Constants.ITEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'=ACTIVE','STOREROOM':'=" + storeroom + "'}}";
            }
        } else {
            if (storeroom == null || storeroom.equals("")) {
                return "{'appid':'" + Constants.ITEM_APPID + "','objectname':'" + Constants.ITEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'=ACTIVE'},'sinorsearch':{'ITEMNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
            } else {
                return "{'appid':'" + Constants.ITEM_APPID + "','objectname':'" + Constants.ITEM_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'=ACTIVE','STOREROOM':'=" + storeroom + "'},'sinorsearch':{'ITEMNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
            }
        }
    }

    /**
     * 设置MEASUREUNIT查询*
     */
    public static String getMEasureunitUrl(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.MEASUREUNIT_NAME + "','objectname':'" + Constants.MEASUREUNIT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.MEASUREUNIT_NAME + "','objectname':'" + Constants.MEASUREUNIT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','sinorsearch':{'MEASUREUNITID':'" + vlaue + "','ABBREVIATION':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置资产查询*
     */
    public static String getAssetUrl(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'!=INACTIVE','TYPE':'=STOREROOM'},'sinorsearch':{'ASSETNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 根据位置查询资产*
     */
    public static String getLocationAssetUrl(String vlaue, String location, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'LOCATION':'=" + location + "'}}";
        } else {
            return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'LOCATION':'=" + location + "'},'sinorsearch':{'ASSETNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 根据位置与资产编号查询资产*
     */
    public static String getLocationAndItemnumAssetUrl(String vlaue, String location, String itemnum, int curpage, int showcount) {
        if (vlaue.equals("")) {
            if (null == location) {
                return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ITEMNUM':'=" + itemnum + "'}}";
            } else {
                return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'LOCATION':'=" + location + "','ITEMNUM':'=" + itemnum + "'}}";
            }
        } else {
            if (null == location) {
                return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ITEMNUM':'=" + itemnum + "'},'sinorsearch':{'ASSETNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";

            } else {
                return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'LOCATION':'=" + location + "','ITEMNUM':'=" + itemnum + "'},'sinorsearch':{'ASSETNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";

            }
        }
    }


    /**
     * 根据位置与状态查询资产*
     */
    public static String getLocationAndStatusAssetUrl(String vlaue, String location, String status, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'LOCATION':'=" + location + "','STATUS':'=" + status + "'}}";
        } else {
            return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'LOCATION':'=" + location + "','STATUS':'=" + status + "'},'sinorsearch':{'ASSETNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 资产
     **/
    public static String getAssetUrl(String location, String sn, String item, int curpage, int showcount) {
        if (!location.equals("")) {
            location = "=" + location;
        }
        if (!sn.equals("")) {
            sn = "=" + sn;
        }
        if (!item.equals("")) {
            item = "=" + item;
        }
        return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM ASC','condition':{'LOCATION':'" + location + "','SERIALNUM':'" + sn + "','ITEMNUM':'" + item + "'}}";
    }


    /**
     * 根据serialnum查询Asset
     **/
    public static String getAssetUrl(String sn) {

        return "{'appid':'" + Constants.UDMPTASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':1,'showcount':20" + ",'option':'read','orderby':'ASSETNUM ASC','condition':{'SERIALNUM':'=" + sn + "'}}";
    }

    public static String getLineUrl(String stocktnum) {

        return "{'appid':'" + Constants.UDSTOCKT_APPID + "','objectname':'" + Constants.UDSCANSN_NAME + "','option':'read','orderby':'LINE ASC','condition':{'STOCKTNUM':'=" + stocktnum + "'}}";
    }


    /**
     * 设置资产转移*
     */
    public static String getINVUSEURL(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.ASOUTB_NAME + "','objectname':'" + Constants.INVUSE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'INVUSENUM ASC','condition':{'APPTYPE':'=AT'}}";
        } else {
            return "{'appid':'" + Constants.ASOUTB_NAME + "','objectname':'" + Constants.INVUSE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'INVUSENUM ASC','condition':{'APPTYPE':'=AT'},'sinorsearch':{'INVUSENUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置资产转移行表*
     */
    public static String getINVUSELINEURL(String invusenum, int curpage, int showcount, String sn) {
        if ("".equals(sn)) {
            return "{'appid':'" + Constants.ASOUTB_NAME + "','objectname':'" + Constants.INVUSELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'INVUSENUM':'=" + invusenum + "'}}";
        } else {
            return "{'appid':'" + Constants.ASOUTB_NAME + "','objectname':'" + Constants.INVUSELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'INVUSENUM':'=" + invusenum + "','SERIALNUM':'=" + sn + "'}}";
        }

    }


    /**
     * 设置根据位置选择*
     */
    public static String getINVENTORYURL(String vlaue, String location, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.ASOUTB_NAME + "','objectname':'" + Constants.INVENTORY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM ASC','condition':{'LOCATION':'" + location + "'}}";
        } else {
            return "{'appid':'" + Constants.ASOUTB_NAME + "','objectname':'" + Constants.INVENTORY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM ASC','condition':{'LOCATION':'" + location + "'},'sinorsearch':{'ITEMNUM':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置根据资产选择*
     */
    public static String getINVENTORYURL1(String vlaue, String itemnum, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.ASOUTB_NAME + "','objectname':'" + Constants.INVENTORY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM ASC','condition':{'ITEMNUM':'" + itemnum + "'}}";
        } else {
            return "{'appid':'" + Constants.ASOUTB_NAME + "','objectname':'" + Constants.INVENTORY_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ITEMNUM ASC','condition':{'ITEMNUM':'" + itemnum + "'},'sinorsearch':{'ITEMNUM':'" + vlaue + "'}}";
        }
    }


    /**
     * 设置资产维修*
     */
    public static String getUDASSTURL(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.UDASREP_NAME + "','objectname':'" + Constants.UDASST_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'UDASSTNUM DESC'}";
        } else {
            return "{'appid':'" + Constants.UDASREP_NAME + "','objectname':'" + Constants.UDASST_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read',,'orderby':'UDASSTNUM DESC','sinorsearch':{'REPAIRNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置资产拆除*
     */
    public static String getDismantleURL(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.DISMANTLE_APPID + "','objectname':'" + Constants.DISMANTLE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'UDORDERNUM DESC'}";
        } else {
            return "{'appid':'" + Constants.DISMANTLE_APPID + "','objectname':'" + Constants.DISMANTLE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read',,'orderby':'UDORDERNUM DESC','sinorsearch':{'UDORDERNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置资产维修行表*
     */
    public static String getUDASSTREPURL(String udasstnum, int curpage, int showcount) {
        return "{'appid':'" + Constants.UDASREP_NAME + "','objectname':'" + Constants.UDASSTREP_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'REPAIRNUM':'=" + udasstnum + "'}}";

    }

    /**
     * 设置资产报废*
     */
    public static String getUDRETIREURL(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.UDRETIRE_APPID + "','objectname':'" + Constants.UDRETIRE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'RETIRENUM DESC'}";
        } else {
            return "{'appid':'" + Constants.UDRETIRE_APPID + "','objectname':'" + Constants.UDRETIRE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'RETIRENUM DESC','sinorsearch':{'RETIRENUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置资产维修行表*
     */
    public static String getUDRETIRELINEURL(String retirenum, int curpage, int showcount, String sn) {
        if (sn.equals("")) {
            return "{'appid':'" + Constants.UDRETIRE_APPID + "','objectname':'" + Constants.UDRETIRELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'RETIRENUM':'=" + retirenum + "'}}";
        } else {
            return "{'appid':'" + Constants.UDRETIRE_APPID + "','objectname':'" + Constants.UDRETIRELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'RETIRENUM':'=" + retirenum + "','SERIAL':'=" + sn + "'}}";
        }
    }

    /**
     * 设置资产移动*
     */
    public static String getUDASSETTRANSFURL(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.ASTRANSF_APPID + "','objectname':'" + Constants.UDASSETTRANSF_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETTRANNUM DESC'}";
        } else {
            return "{'appid':'" + Constants.ASTRANSF_APPID + "','objectname':'" + Constants.UDASSETTRANSF_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETTRANNUM DESC','sinorsearch':{'ASSETTRANNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置资产移动行表*
     */
    public static String getUDTRANSFLINEURL(String assettrannum, int curpage, int showcount, String sn) {
        if ("".equals(sn)) {
            return "{'appid':'" + Constants.ASTRANSF_APPID + "','objectname':'" + Constants.UDTRANSFLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSETTRANNUM':'=" + assettrannum + "'}}";
        } else {
            return "{'appid':'" + Constants.ASTRANSF_APPID + "','objectname':'" + Constants.UDTRANSFLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'ASSETTRANNUM':'=" + assettrannum + "','SERIALNUM':'=" + sn + "'}}";

        }

    }


    /**
     * 设置物料出库*
     */
    public static String getWORKORDERURL(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.WOFMOUT_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.WOFMOUT_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','sinorsearch':{'WONUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置物料出库计划行表*
     */
    public static String getWPMATERIALURL(String wonum, int curpage, int showcount) {
        return "{'appid':'" + Constants.WOFMOUT_APPID + "','objectname':'" + Constants.WPMATERIAL_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'WONUM':'=" + wonum + "'}}";

    }

    /**
     * 设置物料出库计划行表*
     */
    public static String getMATUSETRANSURL(String wonum, int curpage, int showcount, String sn) {
        if ("".equals(sn)) {
            return "{'appid':'" + Constants.WOFMOUT_APPID + "','objectname':'" + Constants.MATUSETRANS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'REFWO':'=" + wonum + "'}}";
        } else {
            return "{'appid':'" + Constants.WOFMOUT_APPID + "','objectname':'" + Constants.MATUSETRANS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'REFWO':'=" + wonum + "','SERIALNUM':'=" + sn + "'}}";
        }
    }


    /**
     * 设置物料退库*
     */
    public static String getMAINVUSE(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.MATREF_APPID + "','objectname':'" + Constants.MAINVUSE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'APPTYPE':'=MR'}}";
        } else {
            return "{'appid':'" + Constants.MATREF_APPID + "','objectname':'" + Constants.MAINVUSE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'APPTYPE':'=MR'},'sinorsearch':{'INVUSENUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }


    /**
     * 设置物料退库弹出框*
     */
    public static String getMAINVUSE(String vlaue, String invusenum, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.MATREF_APPID + "','objectname':'" + Constants.GETREFUNDLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'INVUSENUM':'=" + invusenum + "'}}";
        } else {
            return "{'appid':'" + Constants.MATREF_APPID + "','objectname':'" + Constants.GETREFUNDLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'INVUSENUM':'=" + invusenum + "'},'sinorsearch':{'ITEMNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }


    /**
     * 设置物料出库计划行表*
     */
    public static String getMAINVUSELINEURL(String invusenum, int curpage, int showcount) {
        return "{'appid':'" + Constants.MATREF_APPID + "','objectname':'" + Constants.MAINVUSELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'INVUSENUM':'=" + invusenum + "'}}";

    }


    /**
     * 设置物料盘点*
     */
    public static String getUDSTOCKT(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.UDSTOCKT_APPID + "','objectname':'" + Constants.UDSTOCKT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.UDSTOCKT_APPID + "','objectname':'" + Constants.UDSTOCKT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','sinorsearch':{'STOCKTNUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "','LOCATION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置物料盘点行*
     */
    public static String getUDSTOCKTLINEURL(String stocktnum, int curpage, int showcount) {
        return "{'appid':'" + Constants.UDSTOCKT_APPID + "','objectname':'" + Constants.UDSTOCKTLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STOCKTNUM':'=" + stocktnum + "'}}";

    }

    public static String getUDSTOCKTLINEURL(String stocktnum) {
        return "{'appid':'" + Constants.UDSTOCKT_APPID + "','objectname':'" + Constants.UDSTOCKTLINE_NAME + "','option':'read','condition':{'STOCKTNUM':'=" + stocktnum + "'}}";

    }

    public static String getDismantleLineURL(String udordernum, int curpage, int showcount) {
        return "{'appid':'" + Constants.DISMANTLE_APPID + "','objectname':'" + Constants.DISMANTLELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'UDORDERNUM':'=" + udordernum + "'}}";

    }

    public static String getDismantleLineURL(String udordernum, int curpage, int showcount, String sn) {
        if ("".equals(sn)) {
            return "{'appid':'" + Constants.DISMANTLE_APPID + "','objectname':'" + Constants.DISMANTLELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'UDORDERNUM':'=" + udordernum + "'}}";
        } else {
            return "{'appid':'" + Constants.DISMANTLE_APPID + "','objectname':'" + Constants.DISMANTLELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'UDORDERNUM':'=" + udordernum + "','SERIAL':'=" + sn + "'}}";
        }
    }

    public static String getUdreplaceURL(String udordernum, int curpage, int showcount) {
        return "{'appid':'" + Constants.DISMANTLE_APPID + "','objectname':'" + Constants.UDREPLACE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'UDORDERNUM':'=" + udordernum + "'}}";

    }


    /**
     * 设置采购接收*
     */
    public static String getPOURL(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.WHIN_APPID + "','objectname':'" + Constants.PO_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.WHIN_APPID + "','objectname':'" + Constants.PO_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','sinorsearch':{'PONUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置采购接收行*
     */
    public static String getPOLINEURL(String ponum, int curpage, int showcount, String sn) {
        if (sn.equals("")) {
            return "{'appid':'" + Constants.WHIN_APPID + "','objectname':'" + Constants.POLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PONUM':'=" + ponum + "'}}";
        } else {
            return "{'appid':'" + Constants.WHIN_APPID + "','objectname':'" + Constants.POLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PONUM':'=" + ponum + "','SERIALNUM':'=" + sn + "'}}";
        }

    }


    /**
     * 设置物料转移*
     */
    public static String getMAAINVUSE(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.MATOUTB_APPID + "','objectname':'" + Constants.MAINVUSE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'INVUSENUM ASC','condition':{'APPTYPE':'=MT'}}";
        } else {
            return "{'appid':'" + Constants.MATOUTB_APPID + "','objectname':'" + Constants.MAINVUSE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'INVUSENUM ASC','condition':{'APPTYPE':'=MT'},'sinorsearch':{'INVUSENUM':'" + vlaue + "','DESCRIPTION':'" + vlaue + "'}}";
        }
    }

    /**
     * 设置物料转移行表*
     */
    public static String getMAAINVUSELINEURL(String invusenum, int curpage, int showcount) {
        return "{'appid':'" + Constants.MATOUTB_APPID + "','objectname':'" + Constants.MAINVUSELINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'INVUSENUM':'=" + invusenum + "'}}";

    }

    /**
     * UDBOQLIST*
     */
    public static String getUDBOQLISTURL(String ponum, int curpage, int showcount, String sn) {
        if ("".equals(sn)) {
            return "{'appid':'" + Constants.UDBOQLIST_APPID + "','objectname':'" + Constants.UDBOQLIST_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PONUM':'=" + ponum + "'}}";
        } else {
            return "{'appid':'" + Constants.UDBOQLIST_APPID + "','objectname':'" + Constants.UDBOQLIST_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'PONUM':'=" + ponum + "','SERIALNUM':'=" + sn + "'}}";
        }
    }


    /**
     * 设置Vendor选项*
     */
    public static String getVendorUrl(String vlaue, int curpage, int showcount) {
        if (vlaue.equals("")) {
            return "{'appid':'" + Constants.UDSTOCKT_APPID + "','objectname':'" + Constants.COMPANIES_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read'}";
        } else {
            return "{'appid':'" + Constants.UDSTOCKT_APPID + "','objectname':'" + Constants.COMPANIES_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','sinorsearch':{'COMPANY':'" + vlaue + "','NAME':'" + vlaue + "'}}";
        }
    }


    /**
     * 使用用户名密码登录
     *
     * @param cxt
     * @param username 用户名
     * @param password 密码
     * @param imei     密码
     * @param handler  返回结果处理
     */
    public static void loginWithUsername(final Context cxt, final String username, final String password, String imei,
                                         final HttpRequestHandler<String> handler) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.SIGN_IN_URL;

        Log.i(TAG, "ip_adress=" + ip_adress);
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("loginid", username);
        params.put("password", password);
        params.put("imei", imei);
        client.setTimeout(20000);
        client.post(ip_adress, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
                if (statusCode == 200) {
                    LoginResults loginResults = JsonUtils.parsingAuthStr(cxt, responseString);
                    if (loginResults != null) {
                        if (loginResults.getErrcode().equals(Constants.LOGINSUCCESS) || loginResults.getErrcode().equals(Constants.CHANGEIMEI)) {
                            SafeHandler.onSuccess(handler, loginResults.getResult());
                        } else if (loginResults.getErrcode().equals(Constants.USERNAMEERROR)) {
                            SafeHandler.onFailure(handler, loginResults.getErrmsg());
                        }
                    }

                }
            }
        });


    }


    /**
     * 不分页获取信息方法*
     */
    public static void getData(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        String url = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.setTimeout(20000);
        client.get(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Results result = JsonUtils.parsingResults1(cxt, responseString);
                if (null == result) {
                    SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
                } else {
                    assert result != null;
                    SafeHandler.onSuccess(handler, result);
                    //SafeHandler.onSuccess(handler,result);

                }

            }
        });
    }


    /**
     * 解析返回的结果--分页*
     */
    public static void getDataPagingInfo(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        String url = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        Log.i(TAG, "url=" + url);
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.setTimeout(20000);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Results result = JsonUtils.parsingResults(cxt, responseString);
                if (null == result) {
                    SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
                } else {
                    assert result != null;
                    SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());
                    //SafeHandler.onSuccess(handler,result);

                }
            }
        });
    }

    /**
     * 解析返回的结果--分页*
     */
    public static void getDataPagingInfo1(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        String url = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        Log.i(TAG, "url=" + url);
        SyncHttpClient client = new SyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.setTimeout(20000);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Results result = JsonUtils.parsingResults(cxt, responseString);
                if (null == result) {

                    SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
                } else {
                    assert result != null;

                    SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());

                }
            }
        });
    }

    /**
     * 解析返回的结果--分页*
     */
    public static void getDataPagingInfo2(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        String url = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        Log.i(TAG, "url=" + url);
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.setTimeout(20000);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Results result = JsonUtils.parsingResults(cxt, responseString);
                if (null == result) {

                    SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
                } else {
                    assert result != null;

                    SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());
                    //SafeHandler.onSuccess(handler,result);
                }
            }
        });
    }


}
