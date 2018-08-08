package setra.propulsar.com.sectrab.domainlayer.ws;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import setra.propulsar.com.sectrab.R;

public class WS {

    private static WS instance;
    private static String email;

    public static Context context;
    private static HttpURLConnection con = null;

    private static AsyncTask<Void,Void,JSONObject> async;

    public synchronized static WS getInstance(Context c){

        return instance;
    }

    public class NullInstanceException extends Exception{
        String message;

        public NullInstanceException(){super();}

        public NullInstanceException(String msg){
            super(msg);
            this.message=msg;
        }

        public NullInstanceException(String msg, Throwable cause){
            super(msg, cause);
            this.initCause(cause);
            this.message=msg;
        }
    }

    // ------------------------------------------- //
    // -------------- WEB SERVICES --------------- //
    // ------------------------------------------- //

    public static void userSignIn(Map<String,Object> params, OnWSRequested listener){
        Log.d("userSignIn"," ----- userSignInRequested ----- ");
        String urlString = WS_URL+WS_userSignInURL;
        performRequest(urlString, WS_userSignIn, params, POSTID, listener);
    }

    public static void registerMail(Map<String,Object> params, OnWSRequested listener){
        Log.d("registerMail", " ----- registerMailRequested ----- ");
        String urlString = WS_URL+WS_registerMailURL;
        performRequest(urlString, WS_registerMail, params, POSTID, listener);
    }

    public static void recoverPassword(Map<String,Object> params, OnWSRequested listener){
        Log.d("recoverPassword", " ----- recoverPasswordRequested ----- ");
        String urlString = WS_URL+WS_recoverPasswordURL;
        performRequest(urlString, WS_recoverPassword, params, POSTID, listener);
    }


    // ------------------------------------------- //
    // ------------- WEB IMPLEMENTS -------------- //
    // ------------------------------------------- //

    private static class Task extends AsyncTask<Void,Void,JSONObject> {
        final String urlString;
        final int wsReq;
        final Map<String, Object> params;
        final int postGet;
        final OnWSRequested provListener;

        public Task(final String urlString_, final int wsReq_, final Map<String, Object> params_, final int postGet_, final OnWSRequested provListener_) {
            urlString = urlString_;
            wsReq = wsReq_;
            params = params_;
            postGet = postGet_;
            provListener = provListener_;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try{
                URL url = new URL(urlString);
                if(postGet==MULTIPARTID){
                    String restURL="";
                    for (Map.Entry<String,Object> param : params.entrySet()) {
                        if (param.getKey().equals("link")) {
                            restURL = param.getValue().toString();
                        }
                    }
                    url = new URL(urlString+"/"+restURL);
                    url = new URL(WS.WS_URL+"Message/SendImageMessages");
                    Log.d("urlString","FINALSTRING = "+url.toString());
                }
                StringBuilder postData = new StringBuilder();
                if(postGet!=MULTIPARTID)
                    for (Map.Entry<String,Object> param : params.entrySet()) {
                        Log.d("jsonData","length = "+postData.length());
                        if (postData.length() != 0) {
                            postData.append('&');
                        }

                        if (postData.length() == 0 && postGet==GETID){postData.append('?');}
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                Log.d("WSDeb","postData.toString()="+urlString+postData.toString());
                if(postGet==GETID){
                    url = new URL(urlString.concat(postData.toString()));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                con = (HttpURLConnection)url.openConnection();
                if(postGet==POSTID) {
                    Log.d("method","POST METHOD");
                    con.setRequestMethod("POST");
                }else if(postGet==GETID){
                    Log.d("method","GET METHOD");
                    con.setRequestMethod("GET");
                }
                con.setConnectTimeout(10 * 1000);
                if(postGet!=MULTIPARTID)
                    con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                else{
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("Cache-Control", "no-cache");

                    con.setRequestProperty(
                            "Content-Type", "multipart/form-data;boundary=" + "*****");
                    DataOutputStream request = new DataOutputStream(con.getOutputStream());

                    String attName=""; String attFileName="";
                    Bitmap bitmap=null;
                    for (Map.Entry<String,Object> param : params.entrySet()){
                        if(param.getKey().equals("name")){attName=param.getValue().toString();}
                        if(param.getKey().equals("namefull")){attFileName=param.getValue().toString();}
                        if(param.getKey().equals("image")){bitmap = (Bitmap)param.getValue();}
                    }
                    request.writeBytes("--"/*twohyphens*/ + "*****"/*boundary*/ + "\r\n"/*crlf*/);
                    request.writeBytes("Content-Disposition: form-data; name=\"" +
                            attName + "\";filename=\"" +
                            attFileName + "\"" + "\r\n"/*crlf*/);
                    request.writeBytes("\r\n"/*crlf*/);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    request.writeBytes("\r\n"/*crlf*/);
                    request.writeBytes("--"/*twohyphens*/ + "*****"/*boundary*/ +
                            "--"/*twohyphens*/ + "\r\n"/*crlf*/);
                    request.flush();
                    request.close();
                }
                if(postGet==POSTID)
                    con.setRequestProperty("Content-Length", postDataBytes.length + "");
                if(postGet!=MULTIPARTID) {
                    con.setDoInput(true);
                    con.setUseCaches(false);
                }
                if(postGet==POSTID) {
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    outputStream.write(postDataBytes);
                    outputStream.close();
                }
                int statusCode = con.getResponseCode();

                if(statusCode==200) {
                    Log.d("WSDEB","RETURNED SUCCESFUL");
                    InputStream is = con.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line=null;
                    StringBuffer response = new StringBuffer();
                    while( (line = rd.readLine()) != null) {
                        if (line!=null)
                            response.append(line);
                    }
                    rd.close();
                    JSONObject json = new JSONObject(response.toString());
                    JSONObject jsonRes = new JSONObject();
                    jsonRes.put("status",0);
                    jsonRes.put("ws",wsReq);
                    jsonRes.put("data",json);
                    //createCache(wsReq,jsonRes);

                    //Toast.makeText(context, json.toString(), Toast.LENGTH_SHORT).show();

                    return jsonRes;
                }
                else{
                    Log.d("WSDEB","RETURNED FAILURE");
                    InputStream is = con.getErrorStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line=null;
                    StringBuffer response = new StringBuffer();
                    while( (line = rd.readLine()) != null) {
                        if (line!=null)
                            response.append(line);
                    }
                    rd.close();
                    Log.e("HTTPDEBBUG","StatusCode="+statusCode);
                    Log.e("HTTPDEBBUG","Response="+response);

                    JSONObject jsonRes = new JSONObject();
                    jsonRes.put("status",-1);
                    jsonRes.put("ws",wsReq);
                    jsonRes.put("data",null);

                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Ocurrió un error, intenta nuevamente más tarde", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return jsonRes;
                }

            }catch(Exception e){
                try {
                    e.printStackTrace();
                    JSONObject jsonRes = new JSONObject();
                    jsonRes.put("status", -1);
                    jsonRes.put("ws", wsReq);
                    jsonRes.put("data", null);
                    return jsonRes;
                }catch(Exception ex){}
                return null;
            }

        }
        @Override
        protected void onPostExecute(final JSONObject jsonRes) {
            super.onPostExecute(jsonRes);
            if(jsonRes!=null){
                //Toast.makeText(context,jsonRes.toString(), Toast.LENGTH_LONG).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        provListener.wsAnswered(jsonRes);
                        //async.execute();
                    }
                }, 1);
            }
        }
    }



    private static JSONObject performRequest(final String urlString, final int wsReq, final Map<String,Object> params, final int postGet, final OnWSRequested provListener){
        async = new Task(urlString,wsReq,params,postGet,provListener);
        async.execute();

        return null;
    }

    // ------------------------------------------- //
    // -------------- SHOW MESSAGE --------------- //
    // ------------------------------------------- //

    public static void showMessage(String message, Activity activity){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        builder.setTitle(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void showSucces(String msg, View view) {

        Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        View snackBarView = snack.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorAccent));
        snack.setAction("Action", null).show();
        snack.show();
    }

    public static void showError(String msg, View view) {

        Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        View snackBarView = snack.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.buttonRed));
        snack.setAction("Action", null).show();
        snack.show();
    }


    // ------------------------------------------- //
    // -------------- OWN LISTENER --------------- //
    // ------------------------------------------- //

    public interface OnWSRequested{
        public void wsAnswered(JSONObject json);
    }

    public static String WS_URL;

    public static final int WS_userSignIn = 100;
    public static final int WS_registerMail = 1600;
    public static final int WS_recoverPassword=2500;

    private static final int GETID = 10;
    private static final int POSTID = 11;
    private static final int MULTIPARTID = 12;

    public static final String WS_userSignInURL = "UserSession/UserSignin";
    public static final String WS_registerMailURL="User/UserRegistrationWithEmail ";
    public static final String WS_recoverPasswordURL = "User/RecoveryPassword";
}