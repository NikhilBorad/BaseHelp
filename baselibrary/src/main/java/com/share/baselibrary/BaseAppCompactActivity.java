package com.share.baselibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by qlooit-9 on 20/12/17.
 */

public class BaseAppCompactActivity extends AppCompatActivity {

    public SharedPreferences preferencesVal;
    public SharedPreferences.Editor editor;
    private DialogPlus dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferencesVal = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

    }

    public void updatePref(String key, String val) {
        editor = preferencesVal.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getPref(String key, String defVal) {
        return preferencesVal.getString(key, defVal);
    }

    public void updatePrefInt(String key, int val) {
        editor = preferencesVal.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public Integer getPrefInt(String key, int defVal) {
        return preferencesVal.getInt(key, defVal);
    }

    public void preToast(String val) {
        Toast customToast = new Toast(getBaseContext());
        customToast = Toast.makeText(getBaseContext(), val, Toast.LENGTH_SHORT);
        customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        customToast.show();
    }

    public void alertBox(String message) {
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

    public void setUpActionBar(String Actiontitle) {
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

    public void setUpActionBarHome(String Actiontitle) {
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

    public void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean isNetworkAvailable(Context context) {

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
            hideKeyboard();
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void deBug(String string) {
        Log.e("DEBUG : ", string);
    }

    public void startDialog() {
        dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.load_dialog_activity))
                .setCancelable(false)
                .setGravity(Gravity.CENTER)
                .create();
        dialog.show();

//        TextView dialog_title = (TextView) dialog.findViewById(R.id.text_dialog_title);
//        dialog_title.setText(mVal);
    }

    public void closeDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public Map<String, String> checkParams(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
            if (pairs.getValue() == null) {
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }

    public boolean isValidString(EditText data) {
        if (data.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

}
