package com.mpt.hxqh.mpt_project.unit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.renderscript.ScriptGroup.Input;
import android.util.Log;

import com.mpt.hxqh.mpt_project.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;


/**
 * Created by Sanjay Kumar on 2/18/2015.
 * Copy by Liying   12/2016
 */

public class SslRequest implements X509TrustManager {

    private static TrustManager[] trustManagers;
    private static final X509Certificate[] _AcceptedIssuers = new
            X509Certificate[]{};

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String
            authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String
            authType) throws CertificateException {
    }

    public boolean isClientTrusted(X509Certificate[] chain) {
        return true;
    }

    public boolean isServerTrusted(X509Certificate[] chain) {
        return true;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return _AcceptedIssuers;
    }

    @SuppressLint("TrulyRandom")
    public static void allowAllSSL(String url, Context mContext) {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }

        });

        SSLContext context = null;
        InputStream inputStream = null;
        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new SslRequest()};
        }

        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
            //自签名证书client.cer放在assets文件夹下
            //File file = new File("ceritify.cer");
            //inputStream = SslRequest.class.getResourceAsStream("ceritify.cer");
            inputStream = mContext.getResources().openRawResource(R.raw.ceritify);
            //File file = new File("D:\\AndroidProgect\\Test_Mpt\\app\\src\\main\\res\\ceritify.cer");
            //inputStream = new FileInputStream(file);

            SSLSocketFactory socketFactory = SSLUtil.getSSLSocketFactory(inputStream);

            if (socketFactory != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);
            }

            /*
             * 使用此方法并配合HttpsURLConnection connection设置（见下面）则可以越过证书验证
             */
//          HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        /*
         * 使用此方法则可以越过证书验证
         */
        /*HttpsURLConnection connection;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            ((HttpsURLConnection) connection).setHostnameVerifier(new AllowAllHostnameVerifier());

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtils.e("Ssl_MalformedURLException&&&&&&&&&&&>>>>>>>>>", e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtils.e("Ssl_IOException&&&&&&&&&&&>>>>>>>>>", e.toString());
        }*/


    }
}