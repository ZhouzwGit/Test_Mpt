package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnEditClickL;
import com.flyco.dialog.widget.NormalEditTextDialog;
import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.dialog.FlippingLoadingDialog;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.unit.SslRequest;
import com.tencent.bugly.Bugly;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "LoginActivity";
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private CheckBox checkBox; //记住密码

    private boolean isRemember; //是否记住密码

    private TextView ipconfig;

    private TextView quit;
    private TextView properties;

    String userName; //用户名

    String userPassWorld; //密码

    String imei; //imei

    protected FlippingLoadingDialog mLoadingDialog;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bugly.init(getApplicationContext(), "344235e03a", true);

        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    @Override
    protected void findViewById() {
        mUsername = (EditText) findViewById(R.id.user_login_id);
        mPassword = (EditText) findViewById(R.id.user_login_password);
        checkBox = (CheckBox) findViewById(R.id.isremenber_password);
        mLogin = (Button) findViewById(R.id.user_login);
        ipconfig = (TextView) findViewById(R.id.ipconfig);
        quit = (TextView) findViewById(R.id.quit);
        properties = (TextView) findViewById(R.id.properties);
    }

    @Override
    protected void initView() {

        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        if (isChecked) {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
            checkBox.setChecked(isChecked);
        }
        checkBox.setOnCheckedChangeListener(cheBoxOnCheckedChangListener);
        mLogin.setOnClickListener(this);

        if (AccountUtils.getIpAddress(LoginActivity.this) == null || AccountUtils.getIpAddress(LoginActivity.this).equals("")) {
            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
        }
        ipconfig.setOnClickListener(ipOnClickListener);
        quit.setOnClickListener(quitOnClickListener);
        properties.setOnClickListener(propertiesOnClickListener);
    }

    private CompoundButton.OnCheckedChangeListener cheBoxOnCheckedChangListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isRemember = isChecked;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_login:
                if (mUsername.getText().length() == 0) {
                    mUsername.setError(getString(R.string.login_error_empty_user));
                    mUsername.requestFocus();
                } else if (mPassword.getText().length() == 0) {
                    mPassword.setError(getString(R.string.login_error_empty_passwd));
                    mPassword.requestFocus();
                } else {
                    SslRequest.allowAllSSL(AccountUtils.getIpAddress(LoginActivity.this) + Constants.SIGN_IN_URL, LoginActivity.this);
                    SslRequest.allowAllSSL(AccountUtils.getIpAddress(LoginActivity.this) + Constants.BASE_URL, LoginActivity.this);

                    login();
                }
                break;


        }
    }


    /**
     * 登陆*
     */
    private void login() {

        getLoadingDialog(getString(R.string.login_loging)).show();

        HttpManager.loginWithUsername(LoginActivity.this,
                mUsername.getText().toString(),
                mPassword.getText().toString(), imei,
                new HttpRequestHandler<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.i(TAG, "data=" + data);
                        MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_success));
                        mLoadingDialog.dismiss();
                        if (isRemember) {
                            AccountUtils.setChecked(LoginActivity.this, true);
                            //记住密码
                            AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                        }
                        AccountUtils.saveUser(LoginActivity.this, mUsername.getText().toString());
                        try {//保存登录返回信息
                            JSONObject object = new JSONObject(data);
                            JSONObject LoginDetails = object.getJSONObject("userLoginDetails");
                            AccountUtils.setLoginDetails(LoginActivity.this,
                                    LoginDetails.getString("personId"), object.getString("userName"), LoginDetails.getString("displayName"));
                            Log.i(TAG, "personId" + LoginDetails.getString("personId"));
                        } catch (JSONException e) {
                            Log.i(TAG, "222222");
                            e.printStackTrace();
                        }
                        AccountUtils.setIsOffLine(LoginActivity.this, false);
                        startIntent();

                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));
                        AccountUtils.setIsOffLine(LoginActivity.this, false);
                        startIntent();
                    }

                    @Override
                    public void onFailure(String error) {
                        mLoadingDialog.dismiss();
                        MessageUtils.showErrorMessage(LoginActivity.this, error);

                    }
                });
    }


    private void startIntent() {
        Intent inetnt = new Intent();
        inetnt.setClass(this, MainActivity.class);
        //inetnt.putExtra("user",mUsername.getText().toString());
        startActivityForResult(inetnt, 0);
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }

    private View.OnClickListener ipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setIP();
        }
    };

    private View.OnClickListener quitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppManager.AppExit(LoginActivity.this);
        }
    };

    private View.OnClickListener propertiesOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private void setIP() {
        final NormalEditTextDialog editTextDialog = new NormalEditTextDialog(LoginActivity.this);
        editTextDialog.title("Configure IP")
                .content(AccountUtils.getIpAddress(LoginActivity.this))
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        editTextDialog.setOnBtnClickL(
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        editTextDialog.dismiss();
                    }
                }, new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        AccountUtils.setIpAddress(LoginActivity.this, text);
                        editTextDialog.dismiss();
                    }
                }
        );
    }


    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getResources().getString(R.string.exit_text), Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(LoginActivity.this);
        }
    }

}
