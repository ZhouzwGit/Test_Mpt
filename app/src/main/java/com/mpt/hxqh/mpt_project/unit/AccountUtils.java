package com.mpt.hxqh.mpt_project.unit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mpt.hxqh.mpt_project.R;


/**
 * 登录帐号管理Created by yw on 2015/5/5.
 */
public class AccountUtils {

    public static final int REQUEST_LOGIN = 0;

    private static final String key_login_member = "logined@member";
    private static final String key_fav_nodes = "logined@fav_nodes";


    /**
     * 记录是否记住密码
     *
     * @param cxt
     * @param isChecked *
     */

    public static void setChecked(Context cxt, boolean isChecked) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putBoolean(cxt.getString(R.string.logined_member_ischeck), isChecked).commit();

    }

    /**
     * 读取记住状态*
     */
    public static boolean getIsChecked(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getBoolean(cxt.getString(R.string.logined_member_ischeck), false);
    }

    /**
     * 记录是否离线
     *
     * @param cxt
     * @param isOffLine *
     */

    public static void setIsOffLine(Context cxt, boolean isOffLine) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putBoolean(cxt.getString(R.string.logined_user_isoffline), isOffLine).commit();

    }

    /**
     * 读取是否离线*
     */
    public static boolean getIsOffLine(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getBoolean(cxt.getString(R.string.logined_user_isoffline), false);
    }

    /**
     * 记录用户名与密码
     *
     * @param cxt
     * @param userName
     * @param password
     */

    public static void setUserNameAndPassWord(Context cxt, String userName, String password) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_username), userName).putString(cxt.getString(R.string.logined_member_password), password).commit();
    }

    /**
     * 注销密码
     */

    public static void ClearPassWord(Context cxt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_password), "").commit();
    }

    /**
     * 记录登录返回信息
     *
     * @param cxt
     * @param personId
     */
    public static void setLoginDetails(Context cxt, String personId, String userName, String displayName) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit()
                .putString(cxt.getString(R.string.login_personId), personId).putString(cxt.getString(R.string.login_userName), userName)
                .putString(cxt.getString(R.string.login_displayName), displayName).commit();
    }

    public static String getinsertOrg(Context cxt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.login_insertOrg), "");
    }

    public static String getinsertSite(Context cxt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.login_insertSite), "");
    }

    public static String getpersonId(Context cxt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.login_personId), "");
    }

    public static String getuserName(Context cxt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.login_userName), "");
    }

    public static String getdisplayName(Context cxt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.login_displayName), "");
    }


    /**
     * 获取记住的用户名
     */


    public static String getUserName(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_username), "");
    }

    /**
     * 获取记住的密码
     */


    public static String getUserPassword(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_password), "");
    }


    /**
     * 设置部门
     */

    public static void setDepartment(Context cxt, String department) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.login_department), department).commit();
    }

    /**
     * 获取部门
     */

    public static String getDepartment(Context cxt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.login_department), "");
    }


    /**
     * 设置服务器IP地址*
     */
    public static void setIpAddress(Context cxt, String ip) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.ip_address), ip).commit();

    }

    /**
     * 获取服务器IP地址*
     */
    public static String getIpAddress(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString( cxt.getString(R.string.ip_address), "");
    }
    /*
    * 保存
    * */
    public static void saveUser(Context context,String str){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("nowUser",str).commit();
    }

}
