package mpt.bjhxqh.com.test_mpt;

import android.content.Context;
import android.util.Log;

import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.ui.actvity.MainActivity;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.SslRequest;

import org.junit.Test;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
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

        SslRequest.allowAllSSL("https://45.112.178.173:9080/meaweb/services/MPTMOBILESERVICE?wsdl", new MainActivity());
        //HttpTransportSE httpTransport = new HttpTransportSE(AccountUtils.getIpAddress(context) + url, timeOut);
        try {

            httpTransport.call("http://www.ibm.com/maximo"+"/mptmobileserviceAddMatSto", soapEnvelope);
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
    }
}