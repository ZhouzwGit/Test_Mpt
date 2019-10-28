package com.mpt.hxqh.mpt_project.webserviceclient;

import android.content.Context;
import android.util.Log;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.application.BaseApplication;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.model.WorkFlowResult;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.SSLUtil;
import com.mpt.hxqh.mpt_project.unit.SslRequest;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsServiceConnectionSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by think on 2015/8/11.
 */
public class AndroidClientService {
    private static final String TAG = "AndroidClientService";
    public static String NAMESPACE = "http://www.ibm.com/maximo";//http://www.ibm.com/maximo
    public static String url = "";
    public static int timeOut = 60000;

    public AndroidClientService() {
    }

    public AndroidClientService(String url) {
        this.url = url;
    }

    public void setTimeOut(int seconds) {
        this.timeOut = seconds * 1000;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 开始工作流
     * context 上下文
     * processname 过程名
     * keyValue 对应的
     *
     * @return
     */
    public static WorkFlowResult startwf(Context context, String processname, String mbo, String keyValue, String key, String loginid) {
        Log.i(TAG, "processname=" + processname + ",mbo=" + mbo + ",keyValue=" + keyValue + ",key=" + key + ",loginid=" + loginid);
        String url = Constants.WORK_FLOW_URL;
        Log.e("发送工作流", url);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "wfservicestartWF2");
        soapReq.addProperty("processname", processname);//流程名称
        soapReq.addProperty("mbo", mbo);//如工单WORKORDER
        soapReq.addProperty("keyValue", keyValue);//对应的表ID的值，如工单需要传送WONUM的值，采购申请prnum的值
        soapReq.addProperty("key", key);//对应的表ID，如工单：wonum，采购申请，prnum
        soapReq.addProperty("loginid", loginid);//用户id
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {

            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/wfservicestartWF2", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }


        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WorkFlowResult result = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
            result = JsonUtils.parsingStartWF(obj, key);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return result;
    }

    /**
     * 审批工作流
     *
     * @return
     */
    public static WorkFlowResult approve(Context context, String processname, String mbo, String keyValue, String key, String zx, String desc, String loginid) {
        Log.i(TAG, "processname=" + processname + ",mbo=" + mbo + ",keyValue=" + keyValue + ",key=" + key + ",zx=" + zx + ",desc" + desc + ",loginid=" + loginid);
        String url = Constants.WORK_FLOW_URL;
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "wfservicewfGoOn");
        soapReq.addProperty("processname", processname);//流程名称
        soapReq.addProperty("mboName", mbo);//表名
        soapReq.addProperty("keyValue", keyValue);//表的唯一主键的值（整型数字），当前记录已带，如1232
        soapReq.addProperty("key", key);//表的唯一主键（所有单据规则相同，唯一主键=表名+ID，如工单表ID：WORKORDERID）
        soapReq.addProperty("zx", zx);//审批的结果，1为审批通过，0为审批不通过
        if (!desc.equals("")) {
            soapReq.addProperty("desc", desc);//审批意见
        }
        soapReq.addProperty("loginid", loginid);//用户id
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {

            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/wfservicewfGoOn", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }


        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WorkFlowResult result = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "审批:" + obj);
            result = JsonUtils.parsingGoOn(obj, key);
            Log.i(TAG, "result=" + result);
        } catch (SoapFault soapFault) {
            return null;
        }
        return result;
    }

    /**
     * 资产转移新增方法
     *
     * @return
     */
    public static WebResult AddAssetTrs(Context context, String description, String fromstoreloc, String invowner, String createby, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddAssetTrs");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("FROMSTORELOC", fromstoreloc);//库存
        soapReq.addProperty("INVOWNER", invowner);//仓管员
        soapReq.addProperty("CREATEBY", createby);//创建人
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddAssetTrs", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 资产转移行新增方法
     *
     * @return
     */
    public static WebResult AddAssetTrsLin(Context context, String invusenum, String usetype, String linetype, String itemnum
            , String tostoreloc, String rotassetnum, String issueto, String quantity, String url) {
        String fullUrl = AccountUtils.getIpAddress(context) + url + "?wsdl";
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddAssetTrsLin");
        soapReq.addProperty("INVUSENUM", invusenum);//
        soapReq.addProperty("USETYPE", usetype);//
        soapReq.addProperty("LINETYPE", linetype);//
        soapReq.addProperty("ITEMNUM", itemnum);//
        soapReq.addProperty("TOSTORELOC", tostoreloc);//
        soapReq.addProperty("ROTASSETNUM", rotassetnum);//
        soapReq.addProperty("ISSUETO", issueto);//
        soapReq.addProperty("QUANTITY", quantity);//
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddAssetTrsLin", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }


        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料转移新增方法
     *
     * @return
     */
    public static WebResult AddMatoutb(Context context, String description, String fromstoreloc, String invowner, String usetype, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMatoutb");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("FROMSTORELOC", fromstoreloc);//库存
        soapReq.addProperty("INVOWNER", invowner);//仓管员
        soapReq.addProperty("USETYPE", usetype);//交易类型
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMatoutb", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料转移行新增行方法
     *
     * @return
     */
    public static WebResult AddMatoutbLin(Context context, String invusenum, String itemnum, String frombin, String usetype
            , String linetype, String tositeid, String rotassetnum, String quantity, String tostoreloc, String tobin
            , String issueto, String enterby, String conversion, String url) {
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMatoutbLin");
        soapReq.addProperty("INVUSENUM", invusenum);//
        soapReq.addProperty("ITEMNUM", itemnum);//
        soapReq.addProperty("FROMBIN", frombin);//
        soapReq.addProperty("USETYPE", usetype);//
        soapReq.addProperty("LINETYPE", linetype);//
        soapReq.addProperty("TOSITEID", tositeid);//
        soapReq.addProperty("ROTASSETNUM", rotassetnum);//
        soapReq.addProperty("QUANTITY", quantity);//
        soapReq.addProperty("TOSTORELOC", tostoreloc);//
        soapReq.addProperty("TOBIN", tobin);//
        soapReq.addProperty("ISSUETO", issueto);//
        soapReq.addProperty("ENTERBY", enterby);//
        soapReq.addProperty("CONVERSION", conversion);//
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMatoutbLin", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料退库新增方法
     *
     * @return
     */
    public static WebResult AddMatRf(Context context, String description, String fromstoreloc, String invowner, String createby, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMatRf");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("FROMSTORELOC", fromstoreloc);//库存
        soapReq.addProperty("INVOWNER", invowner);//仓管员
        soapReq.addProperty("CREATEBY", createby);//
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMatRf", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料退库行新增行方法(弹出框)
     *
     * @return
     */
    public static WebResult AddMatRfLin(Context context, String invusenum, String itemnum, String rotassetnum, String enterby
            , String quantity, String linetype, String url) {
        Log.e(TAG, "url=" + AccountUtils.getIpAddress(context) + url);
        Log.e(TAG, "invusenum=" + invusenum + ",itemnum=" + itemnum + ",rotassetnum=" + rotassetnum + ",enterby=" + enterby + ",quantity=" + quantity + ",linetype=" + linetype);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMatRfLin");
        soapReq.addProperty("INVUSENUM", invusenum);//
        soapReq.addProperty("ITEMNUM", itemnum);//
        soapReq.addProperty("ROTASSETNUM", rotassetnum);//
        soapReq.addProperty("ENTERBY", enterby);//
        soapReq.addProperty("QUANTITY", quantity);//
        soapReq.addProperty("LINETYPE", linetype);//
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMatRfLin", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }


    /**
     * 物料退库行新增行方法
     *
     * @return
     */
    public static WebResult AddMatRfLin(Context context, String invusenum, String itemnum, String rotassetnum, String enterby
            , String quantity, String usetype, String linetype, String newphyscnt, String remark, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMatRfLin");
        soapReq.addProperty("INVUSENUM", invusenum);//
        soapReq.addProperty("ITEMNUM", itemnum);//
        soapReq.addProperty("ROTASSETNUM", rotassetnum);//
        soapReq.addProperty("ENTERBY", enterby);//
        soapReq.addProperty("QUANTITY", quantity);//
        soapReq.addProperty("USETYPE", usetype);//
        soapReq.addProperty("LINETYPE", linetype);//
        soapReq.addProperty("NEWPHYSCNT", newphyscnt);//
        soapReq.addProperty("UDREMARK", remark);//
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMatRfLin", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料盘点新增方法
     *
     * @return
     */
    public static WebResult AddMatSto(Context context, String description, String location, String vendor, String createby, String visitd, String url) {
        //ceshi(context);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMatSto");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("LOCATION", location);//位置
        soapReq.addProperty("VENDOR", vendor);//供应商
        soapReq.addProperty("CREATEBY", createby);//创建人
        soapReq.addProperty("VISITD", visitd);//更新时间
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMatSto", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);

            }

            //


        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料盘点行新增行方法
     *
     * @return
     */
    public static WebResult AddMatStoLine(Context context, String stocktnum, String assetnum, String checkserial, String remark
            , String ischeck, String url, BaseApplication application) {
        Log.i(TAG, "stocktnum=" + stocktnum + ",assetnum=" + assetnum + ",checkserial=" + checkserial + ",remark=" + remark + ",ISCHECK=" + ischeck);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMatStoLine");
        soapReq.addProperty("STOCKTNUM", stocktnum);//
        soapReq.addProperty("ASSETNUM", assetnum);//
        soapReq.addProperty("CHECKSERIAL", checkserial);//
        soapReq.addProperty("UDREMARK", remark);//
        soapReq.addProperty("ISCHECK", ischeck);//
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMatStoLine", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * Scan新增方法
     *
     * @return
     */
    public static WebResult udScanSN(Context context, String STOCKTNUM, String serialnum, String creatby, String line, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceudScanSN");
        soapReq.addProperty("STOCKTNUM", STOCKTNUM);
        soapReq.addProperty("SERIALNUM", serialnum);
        soapReq.addProperty("CREATEBY", creatby);
        //soapReq.addProperty("LINE", line);
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceudScanSN", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }


    /**
     * 采购接收新增方法
     *
     * @return
     */
    public static WebResult AddPo(Context context, String description, String userid, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddPo");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("USERID", userid);//位置
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddPo", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 采购接收新增行方法
     *
     * @return
     */
    public static WebResult AddPoLine(Context context, String userid, String ponum, String itemnum, String conversion
            , String orderqty, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddPoLine");
        soapReq.addProperty("USERID", userid);//
        soapReq.addProperty("PONUM", ponum);//
        soapReq.addProperty("ITEMNUM", itemnum);//
        soapReq.addProperty("CONVERSION", conversion);//
        soapReq.addProperty("ORDERQTY", orderqty);//
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddPoLine", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 资产维修新增方法
     *
     * @return
     */
    public static WebResult AddRepair(Context context, String description, String location, String repairdate, String createby, String url) {
        Log.i(TAG, "url=" + AccountUtils.getIpAddress(context) + url);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddRepair");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("LOCATION", location);//位置
        soapReq.addProperty("UDREPDATE", repairdate);//维修时间
        soapReq.addProperty("CREATEBY", createby);//创建人
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddRepair", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 资产维修新增行方法
     *
     * @return
     */
    public static WebResult AddRepairLine(Context context, String repairnum, String udrepdate, String udasstnum, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddRepairLine");
        soapReq.addProperty("REPAIRNUM", repairnum);//资产维修主表单号
        soapReq.addProperty("UDREPDATE", udrepdate);//维修日期
        soapReq.addProperty("UDASSTNUM", udasstnum);//选项框资产编号
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddRepairLine", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 资产报废新增方法
     *
     * @return
     */
    public static WebResult AddRetire(Context context, String description, String location, String retireloc, String retiredate, String createby, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddRetire");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("LOCATION", location);//位置
        soapReq.addProperty("RETIRELOC", retireloc);//报废后位置
        soapReq.addProperty("RETIREDATE", retiredate);//报废日期
        soapReq.addProperty("CREATEBY", createby);//创建人
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddRetire", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 资产报废新增行方法
     *
     * @return
     */
    public static WebResult AddRetireLine(Context context, String repairnum, String assetnum, String retiredate, String retireloc, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddRetireLine");
        soapReq.addProperty("RETIRENUM", repairnum);//资产报废主表单号
        soapReq.addProperty("ASSETNUM", assetnum);//资产编号
        soapReq.addProperty("RETIREDATE", retiredate);//报废日期
        soapReq.addProperty("RETIRELOC", retireloc);//库房
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddRetireLine", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 资产移动新增方法
     *
     * @return
     */
    public static WebResult AddMove(Context context, String description, String fromloc, String tosite, String createby, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMove");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("FROMLOC", fromloc);//来的位置
        soapReq.addProperty("TOSITE", tosite);//目标位置
        soapReq.addProperty("CREATEBY", createby);//创建人
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMove", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 资产移动新增行方法
     *
     * @return
     */
    public static WebResult AddMoveLine(Context context, String assettrannum, String assetnum, String fromsite, String tosite, String createby, String url) {
        Log.i(TAG, "assettrannum=" + assettrannum + ",assetnum=" + assetnum + ",fromsite=" + fromsite + ",tosite=" + tosite + ",createby=" + createby);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddMoveLine");
        soapReq.addProperty("ASSETTRANNUM", assettrannum);//资产移动主表单号
        soapReq.addProperty("ASSETNUM", assetnum);//资产编号
        soapReq.addProperty("FROMSITE", fromsite);//从哪个位置
        soapReq.addProperty("TOSITE", tosite);//到哪个位置
        soapReq.addProperty("CREATEBY", createby);//创建人
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddMoveLine", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料出站新增方法
     *
     * @return
     */
    public static WebResult AddOut(Context context, String description, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddOut");
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddOut", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料出站计划新增方法
     *
     * @return
     */
    public static WebResult AddOutPlanLine(Context context, String wonum, String itemnum,
                                           String description, String location, String issueto, String linetype,
                                           String restype, String quantity, String storelocsite, String unitcost,
                                           String orderunit, String createby, String url) {
        Log.i(TAG, "wonum=" + wonum + ",itemnum=" + itemnum + ",description=" + description + ",location=" + location + ",issueto=" + issueto + ",linetype=" + linetype + ",restype=" + restype + ",quantity="
                + quantity + ",storelocsite=" + storelocsite + ",unitcost=" + unitcost + ",orderunit=" + orderunit + ",createby=" + createby);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddOutPlanLine");
        soapReq.addProperty("WONUM", wonum);//物料出站主表单号
        soapReq.addProperty("ITEMNUM", itemnum);//项目编号
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("LOCATION", location);//位置
        soapReq.addProperty("ISSUETO", issueto);//到哪个位置
        soapReq.addProperty("LINETYPE", linetype);//行类型
        soapReq.addProperty("RESTYPE", restype);//预定类型
        soapReq.addProperty("Quantity", quantity);//数量
        soapReq.addProperty("STORELOCSITE", storelocsite);//库房位置
        soapReq.addProperty("UNITCOST", unitcost);//单位成本
        soapReq.addProperty("ORDERUNIT", orderunit);//单位
        soapReq.addProperty("CREATEBY", createby);//
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddOutPlanLine", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }

        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 物料出站实际新增方法
     *
     * @return
     */
    public static WebResult AddOutActuralLine(Context context, String wonum, String itemnum,
                                              String description, String linetype, String storeloc, String siteid, String quantity,
                                              String unitcost, String location, String trantype, String createby, String rotassetnum, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mptmobileserviceAddOutActuralLine");
        soapReq.addProperty("WONUM", wonum);//物料出站主表单号
        soapReq.addProperty("ITEMNUM", itemnum);//项目编号
        soapReq.addProperty("DESCRIPTION", description);//描述
        soapReq.addProperty("LINETYPE", linetype);//行类型
        soapReq.addProperty("STORELOC", storeloc);//地点
        soapReq.addProperty("SITEID", siteid);//地点
        soapReq.addProperty("Quantity", quantity);//数量
        soapReq.addProperty("UnitCost", unitcost);//单位成本
        soapReq.addProperty("Location", location);//位置
        soapReq.addProperty("TranType", trantype);//交易类型
        soapReq.addProperty("CREATEBY", createby);//创建人
        soapReq.addProperty("ROTASSETNUM", rotassetnum);//rotassetnum
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mptmobileserviceAddOutActuralLine", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();

            Log.i(TAG, "obj=" + obj);
            webResult = JsonUtils.parsingWebResult1(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 通用修改
     */
    public static String UpdateWO(Context context, String json, String mboObjectName, String mboKey, String mboKeyValue, String url) {
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceUpdateMbo");
        soapReq.addProperty("json", json);//参数
        soapReq.addProperty("mboObjectName", mboObjectName);//表名
        soapReq.addProperty("mboKey", mboKey);//表主键 如：WONUM
        soapReq.addProperty("mboKeyValue", mboKeyValue);//表主键值
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mobileserviceUpdateMbo", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        String results = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            results = JsonUtils.parsingString(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return results;
    }
    /**
     * 通用新增
     */
    public static String insertMbo(Context context, String json, String mboObjectName, String mboKey, String url,String personID) {
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceInsertMbo");
        soapReq.addProperty("json", json);//参数
        soapReq.addProperty("mboObjectName", mboObjectName);//表名
        soapReq.addProperty("flag", 1);//表名
        soapReq.addProperty("mboKey", mboKey);//表主键 如：WONUM
        soapReq.addProperty("personId", personID);//表主键值
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                //((HttpsServiceConnectionSE) httpTransport.getServiceConnection()).setSSLSocketFactory();
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mobileserviceInsertMbo", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        String results = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            results = JsonUtils.parsingString(obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return results;
    }

    /**
     * 通过webservice实现图片上传
     *
     * @param imageBuffer
     */
    /**
     * 通用修改
     */
    public static String connectWebService(Context context, String filename, String image, String ownertable, String ownerid, String url) {

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceuploadImage");
        soapReq.addProperty("filename", filename);//文件名
        soapReq.addProperty("image", image);//图片Json
        soapReq.addProperty("ownertable", ownertable);//表名
        soapReq.addProperty("ownerid", ownerid);//表主键值
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mobileserviceuploadImage", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        String webResult = null;
        try {
            webResult = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return webResult;
    }

    /**
     * 通用修改
     */
    public static String permissionWebService(Context context, String name) {

        String url = "/meaweb/services/MOBILESERVICE";
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileservicegetLocaPermission");
        soapReq.addProperty("user", AccountUtils.getuserName(context));//新增信息json
        //soapReq.addProperty("user", "A01934");//新增信息json
        soapEnvelope.setOutputSoapObject(soapReq);
        String host = AccountUtils.getIpAddress(context).split("//")[1].split(":")[0];
        int port = Integer.parseInt(AccountUtils.getIpAddress(context).split("//")[1].split(":")[1]);
        try {
            if (AccountUtils.getIpAddress(context).startsWith("https")) {
                SslRequest.allowAllSSL(AccountUtils.getIpAddress(context) + url + "?wsdl", context);
                HttpsTransportSE httpTransport = new HttpsTransportSE(host, port, url + "?wsdl", timeOut);
                httpTransport.debug = true;
                httpTransport.call(NAMESPACE + "/mobileserviceuploadImage", soapEnvelope);
            } else {
                HttpTransportSE httpTransportSE = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
                httpTransportSE.call("urn:action", soapEnvelope);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;

        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

   /* public static void ceshi(Context context) {
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject("http://www.ibm.com/maximo", "mptmobileserviceAddMatSto");
        soapReq.addProperty("DESCRIPTION", "测试");//描述
        soapReq.addProperty("LOCATION", "AWD00330");//位置
        soapReq.addProperty("VENDOR", "MPT");//供应商
        soapReq.addProperty("CREATEBY", "uattest2");//创建人
        soapReq.addProperty("VISITD", "2019-08-30");//更新时间
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpsTransportSE httpTransport = new HttpsTransportSE("45.112.178.173", 9080, "/meaweb/services/MPTMOBILESERVICE?wsdl", 15000);

        SslRequest.allowAllSSL("https://45.112.178.173:9080/meaweb/services/MPTMOBILESERVICE?wsdl", context);
        //HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
        try {

            httpTransport.call("http://www.ibm.com/maximo" + "/mptmobileserviceAddMatSto", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        String obj = null;
        WebResult webResult = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            System.out.println(obj);
            webResult = JsonUtils.parsingWebResult1(obj);
            System.out.println(webResult.returnStr);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
    }*/
}
