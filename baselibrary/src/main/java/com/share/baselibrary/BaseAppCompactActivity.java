package com.share.baselibrary;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Created by qlooit-9 on 20/12/17.
 */

public class BaseAppCompactActivity extends AppCompatActivity {

    public SharedPreferences preferencesVal;
    public SharedPreferences.Editor editor;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferencesVal = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

    }

    //For update string prefrence
    public void nbUpdatePref(String key, String val) {
        editor = preferencesVal.edit();
        editor.putString(key, val);
        editor.commit();
    }

    //For get string prefrence
    public String nbGetPref(String key, String defVal) {
        return preferencesVal.getString(key, defVal);
    }

    //For update int prefrence
    public void nbUpdatePrefInt(String key, int val) {
        editor = preferencesVal.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    //For get int prefrence
    public Integer nbGetPrefInt(String key, int defVal) {
        return preferencesVal.getInt(key, defVal);
    }

    //For clear all prefrence
    public void nbClearPref() {
        editor.clear();
        editor.apply();
    }

    //For Toast your message
    public void nbToast(String val) {
        Toast customToast = new Toast(getBaseContext());
        customToast = Toast.makeText(getBaseContext(), val, Toast.LENGTH_SHORT);
        customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        customToast.show();
    }

    //For Alert your message
    public void nbAlertBox(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    public void nbSetUpActionBar(String Actiontitle) {
//        getSupportActionBar().setTitle((Html.fromHtml("<ba <font color='#ffffff'>" + Actiontitle + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.actionbar_titleview, null);
        ((TextView) v.findViewById(R.id.title)).setText(Actiontitle);
        getSupportActionBar().setCustomView(v);

    }

    public void nbSetUpActionBarHome(String Actiontitle) {
        getSupportActionBar().setTitle((Html.fromHtml("<ba <font color='#ffffff'>" + Actiontitle + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.actionbar_titleview, null);
        ((TextView) v.findViewById(R.id.title)).setText(Actiontitle);
        getSupportActionBar().setCustomView(v);
    }

    //For hide keyborad
    public void nbHideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //For check internet connection available or not
    public boolean nbIsNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            nbHideKeyboard();
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //For check valid Email
    public final static boolean nbIsValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    //For debug
    public void deBug(String string) {
        Log.e("DEBUG : ", string);
    }

    //For start progrss dialog
    public void nbStartDialog(String message, boolean isCancelable) {
        mProgressDialog = new ProgressDialog(getApplicationContext());
        mProgressDialog.setCancelable(isCancelable);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();

//        TextView dialog_title = (TextView) dialog.findViewById(R.id.text_dialog_title);
//        dialog_title.setText(mVal);
    }

    //For close dialog
    public void nbCloseDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    //For check hash map null or not
    public Map<String, String> nbCheckParams(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
            if (pairs.getValue() == null) {
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }

    //For check edittext is empty
    public boolean nbIsValidString(EditText data) {
        if (data.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    //For load json from assest folder
    public String nbLoadJSONFromAsset(String file_name) {
        String json = null;
        try {
            InputStream is = getAssets().open(file_name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //For play sound
    //raw_data = R.raw.click_sound
    public void nbPlaySound(Context context, int raw_data) {

        MediaPlayer mp = MediaPlayer.create(context, raw_data);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.reset();
                mp.release();
                mp = null;
            }

        });
        try {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
                mp = MediaPlayer.create(context, raw_data);
            }
            mp.setVolume(0.5f, 0.5f);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //For hide status bar
    public void nbHideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);

        }
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        getWindow().setAttributes(params);
    }

    //For check device is mobile ot tablet
    public boolean nbIsPhoneDevice() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    //For check DB existance
    protected boolean nbCheckDataBase(String db_name) {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = "/data/data/" + getPackageName() + "/databases/" + db_name;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    //For get BaseEncode64 String from image
    public String nbGetEncoded64ImageStringFromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return temp;
    }

    //For check app install or not
    public boolean nbCheckAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    //For get image uri
    public Uri nbGetImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    //For save image- handle permission yourself
    public String nbSaveImageBitmap(Bitmap bitmap, String name, String path) {
        File file = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + path);
            myDir.mkdirs();
            file = new File(myDir, name);
            if (file.exists()) {
                return file.toString();
            }
            FileOutputStream out = new FileOutputStream(file);
            //UPDATE IMAGE QUALITY NIKHIL
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.toString();
    }

    //For send mail
    public void nbSendMail(String string, String sub) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{string});
        i.putExtra(Intent.EXTRA_SUBJECT, sub);
        // getResources().getString(R.string.mail_subject));
        i.putExtra(Intent.EXTRA_TEXT, "");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //For dial number
    public void nbDialNumber(String s) {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            nbToast("Can not support calling functionality");
        } else {
            try {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + s));
                startActivity(callIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //For message
    public void nbSendMessage(String stringToCall) {
        Uri smsUri = Uri.parse("smsto:" + stringToCall);
        Intent intentsms = new Intent(Intent.ACTION_SENDTO, smsUri);
        intentsms.putExtra("sms_body", "");
        startActivity(intentsms);
    }

    //For date conversion
    //  ptn1="given date format"
    //  ptn2="desired date format"
    public String nbConvertDate(String ptn1, String ptn2, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ptn1, Locale.US);
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(ptn2, Locale.US);
        return formatter.format(testDate);
    }

    //For get date difference
    public String nbDateDifference(Date startDate, Date endDate) {
        try {
            long different = endDate.getTime() - startDate.getTime();
            if (different <= 0) {
                return "0";
            }
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            String mSec = "";
            if (elapsedSeconds <= 9)
                mSec = "0";
            return " 0" + elapsedMinutes + " : " + mSec + elapsedSeconds + "";
            // return elapsedMinutes + " min " + elapsedSeconds + " Sec";

        } catch (Exception e) {
            return "0";
        }
    }

    //For go to next activity without trace
    public void nbToNextActivityWithoutTrack(Class temp) {

        Intent intent = new Intent(getApplicationContext(), temp);
        startActivity(intent);
        finish();
    }

    //For go to next activity with trace
    public void nbToNextActivity(Class temp) {

        Intent intent = new Intent(getApplicationContext(), temp);
        startActivity(intent);
    }

    //For copy file
    public static void nbCopyFile(File from, File to) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(from));
        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(to));
            try {
                copyAllBytes(in, out);
            } finally {
                safeClose(out);
            }
        } finally {
            safeClose(in);
        }
    }

    private static int copyAllBytes(InputStream in, OutputStream out) throws IOException {
        int byteCount = 0;
        byte[] buffer = new byte[8192];
        while (true) {
            int read = in.read(buffer);
            if (read == -1) {
                break;
            }
            out.write(buffer, 0, read);
            byteCount += read;
        }
        return byteCount;
    }

    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Silent
            }
        }
    }

    //For encode string
    public static String nbEncodeUrl(String stringToEncode) {
        try {
            return URLEncoder.encode(stringToEncode, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1);
        }
    }

    //For decode string
    public static String nbDecodeUrl(String stringToDecode) {
        try {
            return URLDecoder.decode(stringToDecode, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1);
        }
    }

    //For check device is rooted
    public static boolean nbIsDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    //For get sdk version
    public static int nbGetSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    //For get Android ID
    public String nbGetAndroidID() {
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    //For get device manufacture
    public static String nbGetDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    //For get device model
    public static String nbGetDeviceModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }


}
