package com.mpt.hxqh.mpt_project.api;

import android.content.Context;
import android.util.Log;

import com.mpt.hxqh.mpt_project.bean.LoginResults;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.model.ASSET;
import com.mpt.hxqh.mpt_project.model.COMPANIES;
import com.mpt.hxqh.mpt_project.model.DISMANTLE;
import com.mpt.hxqh.mpt_project.model.DISMANTLELINE;
import com.mpt.hxqh.mpt_project.model.GETREFUNDLINE;
import com.mpt.hxqh.mpt_project.model.INVBALANCES;
import com.mpt.hxqh.mpt_project.model.INVENTORY;
import com.mpt.hxqh.mpt_project.model.INVUSE;
import com.mpt.hxqh.mpt_project.model.INVUSELINE;
import com.mpt.hxqh.mpt_project.model.ITEM;
import com.mpt.hxqh.mpt_project.model.LOCATIONS;
import com.mpt.hxqh.mpt_project.model.MAINVUSE;
import com.mpt.hxqh.mpt_project.model.MAINVUSELINE;
import com.mpt.hxqh.mpt_project.model.MATUSETRANS;
import com.mpt.hxqh.mpt_project.model.MEASUREUNIT;
import com.mpt.hxqh.mpt_project.model.PERSON;
import com.mpt.hxqh.mpt_project.model.PO;
import com.mpt.hxqh.mpt_project.model.POLINE;
import com.mpt.hxqh.mpt_project.model.SITE;
import com.mpt.hxqh.mpt_project.model.UDASSETTRANSF;
import com.mpt.hxqh.mpt_project.model.UDASST;
import com.mpt.hxqh.mpt_project.model.UDASSTREP;
import com.mpt.hxqh.mpt_project.model.UDBOQLIST;
import com.mpt.hxqh.mpt_project.model.UDRETIRE;
import com.mpt.hxqh.mpt_project.model.UDRETIRELINE;
import com.mpt.hxqh.mpt_project.model.UDSCANSN;
import com.mpt.hxqh.mpt_project.model.UDSTOCKT;
import com.mpt.hxqh.mpt_project.model.UDSTOCKTLINE;
import com.mpt.hxqh.mpt_project.model.UDTRANSFLINE;
import com.mpt.hxqh.mpt_project.model.WORKORDER;
import com.mpt.hxqh.mpt_project.model.WPMATERIAL;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.model.WorkFlowResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Json数据解析类
 */
public class JsonUtils<E> {
    private static final String TAG = "JsonUtils";


    /**
     * 解析登录信息*
     */
    public static LoginResults parsingAuthStr(final Context cxt, String data) {
        Log.i(TAG, "data=" + data);
        LoginResults loginResults = new LoginResults();
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            String errmsg = json.getString("errmsg");
            loginResults.setErrcode(errcode);
            loginResults.setErrmsg(errmsg);
            if (errcode.equals(Constants.LOGINSUCCESS) || errcode.equals(Constants.CHANGEIMEI)) {
                loginResults.setResult(json.getString("result"));
            }


            return loginResults;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分页解析返回的结果*
     */
    public static Results parsingResults(Context ctx, String data) {
        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                JSONObject rJson = new JSONObject(result);
                String curpage = rJson.getString("curpage");
                String totalresult = rJson.getString("totalresult");
                String resultlist = rJson.getString("resultlist");
                String totalpage = rJson.getString("totalpage");
                String showcount = rJson.getString("showcount");
                results = new Results();
                results.setCurpage(Integer.valueOf(curpage));
                results.setTotalresult(totalresult);
                results.setResultlist(resultlist);
                results.setTotalpage(totalpage);
                results.setShowcount(Integer.valueOf(showcount));
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }

    /**
     * 不分页解析返回的结果*
     */
    public static Results parsingResults1(Context ctx, String data) {

        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                results = new Results();
                results.setResultlist(result);
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }

    /**
     * 不分页解析返回的结果*
     */
    public static WebResult parsingWebResult(String data) {

        WebResult webResult = new WebResult();
        try {
            JSONObject dataJson = new JSONObject(data);
            if (dataJson.has("event")) {
                webResult.event = dataJson.getString("event");
            }
            if (dataJson.has("returnStr")) {
                webResult.returnStr = dataJson.getString("returnStr");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return webResult;
    }



    /**
     * 不分页解析返回的结果*
     */
    public static WebResult parsingWebResult1(String data) {

        String result = null;
        WebResult webResult = new WebResult();
        if (data.contains("|")){
            webResult.returnStr = data.split("\\|")[0];
            webResult.event = data.split("\\|")[1];
        }
        return webResult;
    }


    /**
     * 通用查询更新成功*
     */
    public static String  parsingString(String data) {

        JSONObject object = null;
        String success=null;
        try {
            object = new JSONObject(data);
            if (object.has("success")) {
                success = object.getString("success");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success;
    }


    /**
     * 解析开始工作流返回信息
     *
     * @param data
     * @return
     */
    public static WorkFlowResult parsingStartWF(String data, String num) {
        Log.i(TAG, "data=" + data);
        String woNum = null;
        WorkFlowResult webResult = new WorkFlowResult();
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("errorMsg") && !object.getString("errorMsg").equals("")) {
                webResult.errorMsg = object.getString("errorMsg");
            }
            if (object.has("msg") && !object.getString("msg").equals("")) {
                webResult.errorMsg = object.getString("msg");
            }
            if (object.has(num) && !object.getString(num).equals("")) {
                webResult.wonum = object.getString(num);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return webResult;
    }

    /**
     * 解析审批工作流返回数据
     *
     * @param data
     * @return
     */
    public static WorkFlowResult parsingGoOn(String data, String num) {
        Log.i(TAG, "data=" + data);
        String woNum = null;
        WorkFlowResult webResult = new WorkFlowResult();
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("status") && !object.getString("status").equals("")) {
                webResult.errorMsg = object.getString("status");
            } else if (object.has("massage") && !object.getString("massage").equals("")) {
                webResult.errorMsg = object.getString("massage");
            } else if (object.has("errorMsg") && !object.getString("errorMsg").equals("")) {
                webResult.errorMsg = object.getString("errorMsg");
            }
            if (object.has(num) && !object.getString(num).equals("")) {
                webResult.wonum = object.getString(num);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return webResult;
    }

    /**
     * 位置选择
     */
    public static ArrayList<LOCATIONS> parsingLOCATIONS(Context ctx, String data) {
        ArrayList<LOCATIONS> list = null;
        LOCATIONS locations = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<LOCATIONS>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                locations = new LOCATIONS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = locations.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = locations.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(locations);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = locations.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(locations, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(locations);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ITEM选择
     */
    public static ArrayList<ITEM> parsingITEM(Context ctx, String data) {
        ArrayList<ITEM> list = null;
        ITEM item = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ITEM>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                item = new ITEM();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = item.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = item.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(item);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = item.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(item, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(item);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 人员选择
     */
    public static ArrayList<PERSON> parsingPERSON(Context ctx, String data) {
        ArrayList<PERSON> list = null;
        PERSON person = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<PERSON>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                person = new PERSON();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = person.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = person.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(person);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = person.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(person, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(person);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * INVBALANCES
     */
    public static ArrayList<INVBALANCES> parsingINVBALANCES(Context ctx, String data) {
        ArrayList<INVBALANCES> list = null;
        INVBALANCES invbalances = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVBALANCES>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                invbalances = new INVBALANCES();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = invbalances.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = invbalances.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(invbalances);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = invbalances.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(invbalances, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(invbalances);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * SITE
     */
    public static ArrayList<SITE> parsingSITE(Context ctx, String data) {
        ArrayList<SITE> list = null;
        SITE site = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<SITE>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                site = new SITE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = site.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = site.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(site);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = site.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(site, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(site);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 资产选择
     */
    public static ArrayList<ASSET> parsingASSET(String data) {
        ArrayList<ASSET> list = null;
        ASSET asset = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ASSET>();
            for (int i = 0; i < jsonArray.length(); i++) {
                asset = new ASSET();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = asset.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = asset.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(asset);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = asset.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(asset, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(asset);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * INVENTORY选择
     */
    public static ArrayList<INVENTORY> parsingINVENTORY(String data) {
        ArrayList<INVENTORY> list = null;
        INVENTORY inventory = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVENTORY>();
            for (int i = 0; i < jsonArray.length(); i++) {
                inventory = new INVENTORY();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = inventory.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = inventory.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(inventory);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = inventory.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(inventory, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(inventory);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }










    /**
     */
    public static ArrayList<MEASUREUNIT> parsingMEASUREUNIT(String data) {
        ArrayList<MEASUREUNIT> list = null;
        MEASUREUNIT asset = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<MEASUREUNIT>();
            for (int i = 0; i < jsonArray.length(); i++) {
                asset = new MEASUREUNIT();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = asset.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = asset.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(asset);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = asset.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(asset, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(asset);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

       /**
     * 资产转移
     */
    public static ArrayList<INVUSE> parsingINVUSE(String data) {
        ArrayList<INVUSE> list = null;
        INVUSE invuse = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVUSE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                invuse = new INVUSE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = invuse.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = invuse.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(invuse);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = invuse.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(invuse, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(invuse);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 库存退回弹出框
     */
    public static ArrayList<GETREFUNDLINE> parsingGETREFUNDLINE(String data) {
        ArrayList<GETREFUNDLINE> list = null;
        GETREFUNDLINE getrefundline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<GETREFUNDLINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                getrefundline = new GETREFUNDLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = getrefundline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = getrefundline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(getrefundline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = getrefundline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(getrefundline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(getrefundline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 资产转移行
     */
    public static ArrayList<INVUSELINE> parsingINVUSELINE(String data) {
        ArrayList<INVUSELINE> list = null;
        INVUSELINE invuseline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVUSELINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                invuseline = new INVUSELINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = invuseline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = invuseline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(invuseline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = invuseline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(invuseline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(invuseline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 资产转移行
     */
    public static ArrayList<UDBOQLIST> parsingUDBOQLIST(String data) {
        ArrayList<UDBOQLIST> list = null;
        UDBOQLIST udboqlist = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDBOQLIST>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udboqlist = new UDBOQLIST();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udboqlist.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udboqlist.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udboqlist);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udboqlist.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udboqlist, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udboqlist);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 资产维修
     */
    public static ArrayList<UDASST> parsingUDASST(String data) {
        ArrayList<UDASST> list = null;
        UDASST udasst = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDASST>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udasst = new UDASST();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udasst.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udasst.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udasst);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udasst.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udasst, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udasst);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 资产拆除
     */
    public static ArrayList<DISMANTLE> parsingDISMANTLE(String data) {
        ArrayList<DISMANTLE> list = null;
        DISMANTLE dismantle = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<DISMANTLE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                dismantle = new DISMANTLE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = dismantle.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = dismantle.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(dismantle);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = dismantle.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(dismantle, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(dismantle);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 资产维修行
     */
    public static ArrayList<UDASSTREP> parsingUDASSTREP(String data) {
        ArrayList<UDASSTREP> list = null;
        UDASSTREP udasstrep = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDASSTREP>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udasstrep = new UDASSTREP();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udasstrep.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udasstrep.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udasstrep);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udasstrep.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udasstrep, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udasstrep);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 资产报废
     */
    public static ArrayList<UDRETIRE> parsingUDRETIRE(String data) {
        ArrayList<UDRETIRE> list = null;
        UDRETIRE udretire = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDRETIRE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udretire = new UDRETIRE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udretire.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udretire.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udretire);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udretire.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udretire, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udretire);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 资产报废行
     */
    public static ArrayList<UDRETIRELINE> parsingUDRETIRELINE(String data) {
        ArrayList<UDRETIRELINE> list = null;
        UDRETIRELINE udretireline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDRETIRELINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udretireline = new UDRETIRELINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udretireline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udretireline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udretireline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udretireline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udretireline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udretireline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 资产移动
     */
    public static ArrayList<UDASSETTRANSF> parsingUDASSETTRANSF(String data) {
        ArrayList<UDASSETTRANSF> list = null;
        UDASSETTRANSF udassettransf = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDASSETTRANSF>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udassettransf = new UDASSETTRANSF();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udassettransf.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udassettransf.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udassettransf);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udassettransf.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udassettransf, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udassettransf);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 资产移动行
     */
    public static ArrayList<UDTRANSFLINE> parsingUDTRANSFLINE(String data) {
        ArrayList<UDTRANSFLINE> list = null;
        UDTRANSFLINE udtransfline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDTRANSFLINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udtransfline = new UDTRANSFLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udtransfline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udtransfline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udtransfline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udtransfline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udtransfline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udtransfline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 物料出库
     */
    public static ArrayList<WORKORDER> parsingWORKORDER(String data) {
        ArrayList<WORKORDER> list = null;
        WORKORDER workorder = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WORKORDER>();
            for (int i = 0; i < jsonArray.length(); i++) {
                workorder = new WORKORDER();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = workorder.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = workorder.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(workorder);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = workorder.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(workorder, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(workorder);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 物料出库计划
     */
    public static ArrayList<WPMATERIAL> parsingWPMATERIAL(String data) {
        ArrayList<WPMATERIAL> list = null;
        WPMATERIAL wpmaterial = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WPMATERIAL>();
            for (int i = 0; i < jsonArray.length(); i++) {
                wpmaterial = new WPMATERIAL();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = wpmaterial.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = wpmaterial.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(wpmaterial);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = wpmaterial.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(wpmaterial, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(wpmaterial);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 物料出库实际
     */
    public static ArrayList<MATUSETRANS> parsingMATUSETRANS(String data) {
        ArrayList<MATUSETRANS> list = null;
        MATUSETRANS matusetrans = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<MATUSETRANS>();
            for (int i = 0; i < jsonArray.length(); i++) {
                matusetrans = new MATUSETRANS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = matusetrans.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = matusetrans.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(matusetrans);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = matusetrans.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(matusetrans, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(matusetrans);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<DISMANTLELINE> parsingDismantleLine(String data) {
        ArrayList<DISMANTLELINE> list = null;
        DISMANTLELINE matusetrans = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<DISMANTLELINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                matusetrans = new DISMANTLELINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = matusetrans.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = matusetrans.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(matusetrans);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = matusetrans.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(matusetrans, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(matusetrans);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 物料退库
     */
    public static ArrayList<MAINVUSE> parsingMAINVUSE(String data) {
        ArrayList<MAINVUSE> list = null;
        MAINVUSE mainvuse = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<MAINVUSE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                mainvuse = new MAINVUSE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = mainvuse.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = mainvuse.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(mainvuse);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = mainvuse.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(mainvuse, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(mainvuse);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 物料退库行
     */
    public static ArrayList<MAINVUSELINE> parsingMAINVUSELINE(String data) {
        ArrayList<MAINVUSELINE> list = null;
        MAINVUSELINE mainvuseline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<MAINVUSELINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                mainvuseline = new MAINVUSELINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = mainvuseline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = mainvuseline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(mainvuseline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = mainvuseline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(mainvuseline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(mainvuseline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 物料盘点
     */
    public static ArrayList<UDSTOCKT> parsingUDSTOCKT(String data) {
        ArrayList<UDSTOCKT> list = null;
        UDSTOCKT udstockt = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDSTOCKT>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udstockt = new UDSTOCKT();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udstockt.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udstockt.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udstockt);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udstockt.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udstockt, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udstockt);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 物料盘点
     */
    public static ArrayList<UDSTOCKTLINE> parsingUDSTOCKTLINE(String data) {
        ArrayList<UDSTOCKTLINE> list = null;
        UDSTOCKTLINE udstocktline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDSTOCKTLINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                udstocktline = new UDSTOCKTLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = udstocktline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = udstocktline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(udstocktline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = udstocktline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(udstocktline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(udstocktline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 采购接收*
     */
    public static ArrayList<PO> parsingPO(String data) {
        Log.i(TAG, "udpro data=" + data);
        ArrayList<PO> list = null;
        PO po = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<PO>();
            Log.i(TAG, "jsonArray length=" + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                po = new PO();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = po.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = po.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(po);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = po.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(po, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(po);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 采购接收*
     */
    public static ArrayList<POLINE> parsingPOLINE(String data) {
        ArrayList<POLINE> list = null;
        POLINE poline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<POLINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                poline = new POLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = poline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = poline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(poline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = poline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(poline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(poline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static ArrayList<UDSCANSN> parsingUDSCANSN(String data) {
        ArrayList<UDSCANSN> list = null;
        UDSCANSN poline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<UDSCANSN>();
            for (int i = 0; i < jsonArray.length(); i++) {
                poline = new UDSCANSN();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = poline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = poline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(poline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = poline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(poline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(poline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }




    /**
     * Vendor
     */
    public static ArrayList<COMPANIES> parsingCOMPANIES(String data) {
        ArrayList<COMPANIES> list = null;
        COMPANIES locations = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<COMPANIES>();
            for (int i = 0; i < jsonArray.length(); i++) {
                locations = new COMPANIES();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = locations.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = locations.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(locations);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = locations.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(locations, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(locations);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**封装盘点数据**/
    public static String  encapsulationUdstocktline(UDSTOCKTLINE udstocktline){

        JSONObject json = new JSONObject();

        try {
            json.put("CHECKSERIAL", udstocktline.getCHECKSERIAL());
            json.put("STKRESULT",udstocktline.getSTKRESULT());
            json.put("UDREMARK",udstocktline.getREMARK());
            json.put("QTYINSTK",udstocktline.getQTYINSTK());
            json.put("CHECKDATE",udstocktline.getCHECKDATE());
            json.put("STOCKTAKER",udstocktline.getSTOCKTAKER());
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("", "");
            jsonArray.put(jsonObject);
            json.put("relationShip", jsonArray);
        } catch (JSONException e) {
            return null;
        }

        Log.i(TAG, "json=" + json);
        return json.toString();
    }
    public static String  dismantleJSON(DISMANTLE dismantle){

        JSONObject json = new JSONObject();

        try {
            json.put("DESCRIPTION", dismantle.getDESCRIPTION());
            json.put("LOCATION",dismantle.getLOCATION());
            json.put("CREATEDATE",dismantle.getCREATEDATE());
            json.put("CREATEDATE",dismantle.getCREATEDATE());
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("", "");
            jsonArray.put(jsonObject);
            json.put("relationShip", jsonArray);
        } catch (JSONException e) {
            return null;
        }

        Log.i(TAG, "json=" + json);
        return json.toString();
    }


}