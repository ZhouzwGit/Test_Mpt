package com.mpt.hxqh.mpt_project.unit;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.application.BaseApplication;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * Created by zzw on 2019/8/31.
 */

public class GetSSL {
    private static final String KEY_STORE_TYPE_BKS = "bks";//固定值
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//固定值
    private static final String KEY_STORE_PASSWORD = "123456";//此密码是你生成证书时输入的密码
    private static final String KEY_STORE_TRUST_PASSWORD = "123456";
    private static KeyStore keyStore;
    private static KeyStore trustStore;

    public static SSLSocketFactory getSocketFactory() {
        SSLSocketFactory socketFactory = null;
        try {
            // 服务器端需要验证的客户端证书
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //res/raw/文件夹下的.p12文件
            InputStream ksIn = BaseApplication.getContext().getResources().openRawResource(R.raw.ceritify);
            try {
                keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ksIn.close();
                } catch (Exception ignore) {
                }
            }
            socketFactory = new SSLSocketFactory(keyStore, KEY_STORE_PASSWORD, trustStore);
            socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return socketFactory;
    }
}
