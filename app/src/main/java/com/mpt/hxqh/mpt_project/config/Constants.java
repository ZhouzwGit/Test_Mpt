package com.mpt.hxqh.mpt_project.config;

/**
 * Created by think on 2015/10/19.
 */
public class Constants {


    /**
     * 基础接口*
     */
    /**
     * 默认*
     */
    public static final String HTTP_API_IP = "https://45.112.178.173:9080";
    public static final String PORT = "9080";
    public static final String HOST = "45.112.178.173";
//    public static final String HTTP_API_IP = "http://101.201.79.54:7001";


    /**
     * 登陆URL*
     */

    public static final String SIGN_IN_URL = "/maximo/mobile/system/login";


    /**
     * 通用接口查询*
     */
    public static final String BASE_URL = "/maximo/mobile/common/api";

    /**
     * 通用修改接口*
     */
//    public static final String WORK_URL = "http://192.168.100.17:7001/meaweb/services/MOBILESERVICE";
    public static final String WORK_URL = "/meaweb/services/MOBILESERVICE";
    //public static final String WORK_URL = "/meaweb/wsdl/MOBILESERVICE";

    /**
     * 资产转移新增接口*
     */
    public static final String TRANSFER_URL = "/meaweb/services/MPTMOBILESERVICE";
    //public static final String TRANSFER_URL = "/meaweb/wsdl/MPTMOBILESERVICE";
    /**
     * 工作流*
     */
    public static final String WORK_FLOW_URL = "/meaweb/services/WFSERVICE";
    //public static final String WORK_FLOW_URL = "/meaweb/wsdl/WFSERVICE";

    /**
     * ------------------数据库表名配置－－开始*
     */

    //位置appid
    public static final String LOCATION_APPID = "LOCATION" ;

    //位置的表名
    public static final String LOCATIONS_NAME = "LOCATIONS";

    //ITEMappid
    public static final String ITEM_APPID = "ITEM" ;

    //ITEM的表名
    public static final String ITEM_NAME = "ITEM";

    //人员appid
    public static final String PERSON_APPID = "PERSON" ;

    //人员的表名
    public static final String PERSON_NAME = "PERSON";

    //INVBALANCESappid
    public static final String INVBALANCES_APPID = "INVBALANCES" ;

    //INVBALANCES的表名
    public static final String INVBALANCES_NAME = "INVBALANCES";

    //SITEappid
    public static final String SITE_APPID = "SITE" ;

    //SITE的表名
    public static final String SITE_NAME = "SITE";

    //资产appid
    public static final String UDMPTASSET_APPID = "UDMPTASSET" ;

    //资产的表名
    public static final String ASSET_NAME = "ASSET";


    public static final String UDSCANSN_NAME = "UDSCANSN";

    //MEASUREUNIT的表名
    public static final String MEASUREUNIT_NAME = "MEASUREUNIT";

    //资产转移appid
    public static final String ASOUTB_NAME = "ASOUTB";
    //资产转移的表名
    public static final String INVUSE_NAME = "INVUSE";
    //资产转移行的表名
    public static final String INVUSELINE_NAME = "INVUSELINE";
    //INVENTORY的表名
    public static final String INVENTORY_NAME = "INVENTORY";


    //资产维修appid
    public static final String UDASREP_NAME = "UDASREP";
    //资产维修的表名
    public static final String UDASST_NAME = "UDASST";
    //资产维修行的表名
    public static final String UDASSTREP_NAME = "UDASSTREP";

    //资产报废appid
    public static final String UDRETIRE_APPID = "UDRETIRE";
    //资产报废的表名
    public static final String UDRETIRE_NAME = "UDRETIRE";
    //资产报废行的表名
    public static final String UDRETIRELINE_NAME = "UDRETIRELINE";

    //资产移动appid
    public static final String ASTRANSF_APPID = "ASTRANSF";
    //资产移动的表名
    public static final String UDASSETTRANSF_NAME = "UDASSETTRANSF";
    //资产移动行的表名
    public static final String UDTRANSFLINE_NAME = "UDTRANSFLINE";

    //物料出库appid
    public static final String WOFMOUT_APPID = "WOFMOUT";
    //物料出库的表名
    public static final String WORKORDER_NAME = "WORKORDER";
    //物料出库计划行的表名
    public static final String WPMATERIAL_NAME = "WPMATERIAL";
    //物料出库实际行的表名
    public static final String MATUSETRANS_NAME = "MATUSETRANS";

    //物料退库appid
    public static final String MATREF_APPID = "MATREF";
    //物料转移appid
    public static final String MATOUTB_APPID = "MATOUTB";
    //物料退库的表名
    public static final String MAINVUSE_NAME = "INVUSE";
    //物料退库行的表名
    public static final String GETREFUNDLINE_NAME = "GETREFUNDLINE";

    //物料退库行的表名
    public static final String MAINVUSELINE_NAME = "INVUSELINE";

    //物料盘点appid
    public static final String UDSTOCKT_APPID = "UDSTOCKT";
    //物料盘点的表名
    public static final String UDSTOCKT_NAME = "UDSTOCKT";
    //物料盘点行的表名
    public static final String UDSTOCKTLINE_NAME = "UDSTOCKTLINE";

    //资产拆除Appid
    public static final String DISMANTLE_APPID = "DISMANTLE";
    //资产拆除主表名
    public static final String DISMANTLE_NAME = "UDDISMANTLE";
    public static final String DISMANTLELINE_NAME = "UDDISMANTLELINE";
    public static final String UDREPLACE_NAME = "UDREPLACE";

    //COMPANIES的表名
    public static final String COMPANIES_NAME = "COMPANIES";

    //采购接收appid
    public static final String WHIN_APPID = "WHIN";
    //采购接收的表名
    public static final String PO_NAME = "PO";
    //采购接收行的表名
    public static final String POLINE_NAME = "POLINE";
    //UDBOQLIST APPID
    public static final String UDBOQLIST_APPID = "ITEMREV";
    public static final String UDBOQLIST_NAME = "UDBOQLIST";


     /***数据库表名配置--结束***/

    public static final String USER_INFO = "userinfo";
    public static final String NAME_KEY = "name_key";
    public static final String PASS_KEY = "pass_key";
    public static final String ISREMENBER = "isRemenber";


    /**
     * 用户登录表识--开始*
     */
    public static final String LOGINSUCCESS = "USER-S-101"; //登录成功

    public static final String CHANGEIMEI = "USER-S-104"; //登录成功,检测到用户更换手机登录

    public static final String USERNAMEERROR = "USER-E-100";//用户名密码错误

    public static final String GETDATASUCCESS = "GLOBAL-S-0";//获取数据成功

    /**
     * 工作流状态
     */
    public static final String ASSETREP_START = "ENTERED";//Asset Repair初始状态
    public static final String ASSETREP_END = "WCLOSE";//Asset Repair结束状态
    public static final String ASSTRETIRE_START = "ENTERED";//Asset Retirement初始状态
    public static final String ASSTRETIRE_END = "CLOSE";//Asset Retirement结束状态
    public static final String ASTTRANS_START = "ENTERED";//Asset Transfer初始状态
    public static final String ASTTRANS_END = "CLOSE";//Asset Transfer结束状态
    public static final String MPT_MATRE_START = "ENTERED";//material refund初始状态
    public static final String MPT_MATRE_END = "RECEIVED";//material refund结束状态
    public static final String MPT_MATTF_START = "ENTERED";//material Transfer初始状态
    public static final String MPT_MATTF_END = "RECEIVED";//material Transfer结束状态
    public static final String MPT_STOTAK_START = "WAPPR";//material stocktaking初始状态
    public static final String MPT_STOTAK_END = "CLOSE";//material stocktaking结束状态
    public static final String MPT_WHIN_START = "WAPPR";//
    public static final String MPT_WHIN_END = "CLOSE";//

    /**
     * 工单跳转类型标识
     */
    public static final String FR = "FR";//故障工单
    public static final String AA = "AA";//终验收工单
    public static final String DC = "DC";//调试工单
    public static final String SP = "SP";//排查工单
    public static final String TP = "TP";//技改工单
    public static final String WS = "WS";//定检工单

    /**
     * 工作流名称
     */
    public static final String UDDJWO = "UDDJWO";//定检工单
    public static final String UDGZWO = "UDGZWO";//故障工单
    public static final String UDJGWO = "UDJGWO";//技改工单
    public static final String UDPCWO = "UDPCWO";//排查工单
    public static final String UDZYSWO = "UDZYSWO";//终验收工单
    public static final String UDDEBUGWOR="UDDEBUGWOR";//调试工单
    //工单状态
    public static final String STATUS1 = "新建";
    public static final String STATUS2 = "进行中";





    /**设置数据库参数-开始**/
    /**
     * 数据库路径
     */
    public static final String PATH_DB = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/Android/data/";
    /**
     * 无SD卡的数据库路径
     */
    public static final String NOT_SDCARD_PATH_DB = "/data/data/";
    /**
     * 数据库名称 *
     */
    public static final String TB_NAME = "sqlite-mingyang.db";

    /**
     * 选项跳转请求值
     */
    public static final int PERSONCODE = 100; //人员

    public static final int REGULARINSCODE = 110;//定检计划编号

    public static final int WS_JOBPLANCODE = 120;//定检作业计划

    public static final int UDPROCODE = 130;//项目编号

    public static final int UDLOCNUMCODE = 140;//风机号

    public static final int SP_JOBPLANCODE = 150;//排查作业计划

    public static final int WTCODE = 160;//风机型号

    public static final int TP_JOBPLANCODE = 170;//技改作业计划

    public static final int LOCATIONCODE = 180;//设备位置

    public static final int ZYS_UDPLANNUMCODE = 190;//终验收计划

    public static final int FAILURECODE = 200;//故障类

    public static final int PROBLEMCODE = 210;//问题原因

    public static final int ITEMCODE = 220;//物资编码

    public static final int LOCATIONCODE2 = 230;//库房

    public static final int UDVEHICLE = 240;//车辆

    public static final int WORKORDERCODE = 250;//相关故障工单

    public static final int UDDEPTCODE = 260;//部门

    public static final int WONUMCODE = 270;//业务单号

}
