package com.example.gauravkumar.sample1;


import android.app.Activity;
import android.database.Cursor;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by gaurav.kumar on 22/08/16.
 */
public class WebViewClientImpl extends WebViewClient {


    private Activity activity = null;
    private UrlCache urlCache = null;
    private DBHelper mydb;
    TextView statusB ;
    int id_To_Update=0;
    public int id;
    private  HashMap data_profile;

    public WebViewClientImpl(Activity activity) {
        this.activity = activity;
        this.urlCache = new UrlCache(activity);
        mydb = new DBHelper(activity);

        
        //HashMap data_profile;
        this.urlCache.register("https://www.irctc.co.in/","eticketing/loginHome.jsf",
                "text/html", "UTF-8", 5 * UrlCache.ONE_MINUTE);
//        if (id > 0) {
//            //means this is the view part not the add contact part.
//            Cursor rs = mydb.getData(id);
//            id_To_Update = id;
//            rs.moveToFirst();
//            data_profile = new HashMap<String,String>();
//
//            data_profile.put("id",Integer.toString(id));
//            String uname = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLOUMN_USERNAME));
//            data_profile.put("username",uname);
//            String passwd = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLOUMN_PASSWORD));
//            data_profile.put("password",passwd);
//
//            if (!rs.isClosed()) {
//                rs.close();
//            }
            //statusB = (TextView) activity.findViewById(R.id.webview_status);
    }

    public void setProfileId(int profileId) {
        id = profileId;
        Toast.makeText(this.activity, "Optional Function", Toast.LENGTH_SHORT).show();
        if (id > 0) {
            //means this is the view part not the add contact part.
            Cursor rs = mydb.getData(id);
            id_To_Update = id;
            rs.moveToFirst();
            data_profile = new HashMap<String, String>();

            data_profile.put("id", Integer.toString(id));
            String uname = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLOUMN_USERNAME));
            data_profile.put("username", uname);
            String passwd = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLOUMN_PASSWORD));
            data_profile.put("password", passwd);
            //Todo :: Put all the data in Has Map from data base;
            if (!rs.isClosed()) {
                rs.close();
            }

            Toast.makeText(this.activity, data_profile.get("username").toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed(); // Ignore SSL certificate errors
    }
    @Override
    public void onPageFinished(WebView view, String url) {

        Toast.makeText(this.activity, view.getUrl(), Toast.LENGTH_SHORT).show();
        //statusB.setText("Status :"+view.getUrl().length());
        if (view.getUrl().equalsIgnoreCase("https://www.irctc.co.in/eticketing/loginHome.jsf")) {
            view.loadUrl("javascript: function fillform() { var f0 = document.forms[1]; "+
                    "    f0['j_username'].value = '"+data_profile.get("username").toString()+"';   "+
                    " f0['j_password'].value = '"+data_profile.get("password").toString()+"'; "+
                    "location.href=\"#usernameId\"; f0['j_captcha'].focus(); "+
                    " f0[\"j_captcha\"].style.textTransform=\"uppercase\"; }fillform();");
        }
        if (view.getUrl().equalsIgnoreCase("https://www.irctc.co.in/eticketing/home")) {
            view.loadUrl("javascript: function jpform() {\n" +
                    "f1 = document.forms['jpform']; \n" +
                    "f1.elements[2].value = \"LOKMANYATILAK T - LTT\";\n" +
                    "f1.elements[3].value = \"PATNA JN - PNBE\";\n" +
                    "f1.elements[4].value = \"15-06-2015\";\n" +
                    "f1.elements[8].click();\n" +
                    "\n" +
                    "} jpform();");
        }


        if (view.getUrl().equalsIgnoreCase("https://www.irctc.co.in/eticketing/trainbetweenstns.jsf")){
            view.loadUrl("function addPassanger(){\n" +
                    "f3= document.forms['addPassengerForm']\n" +
                    "f3[\"addPassengerForm:psdetail:0:psgnName\"].value='Gaurav Kumar'\n" +
                    "f3[\"addPassengerForm:psdetail:0:psgnAge\"].value='21'\n" +
                    "f3[\"addPassengerForm:psdetail:0:psgnGender\"].value='M'\n" +
                    "f3[\"addPassengerForm:psdetail:0:berthChoice\"].value='LB'\n" +
                    "f3['addPassengerForm:mobileNo'].value='9560279113'\n" +
                    "f3['addPassengerForm:psdetail:0:idCardType'].value='PANCARD'\n" +
                    "f3[\"addPassengerForm:psdetail:0:idCardNumber\"].value='AUX45UV'\n" +
                    "location.href=\"#j_captcha\"\n" +
                    "var textbox = document.getElementById(\"j_captcha\");\n" +
                    "    textbox.focus();\n" +
                    "    textbox.scrollIntoView();\n" +
                    "}\n" +
                    "addPassanger();");
        }
    }

}

