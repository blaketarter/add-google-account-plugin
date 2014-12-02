package com.btarter.GoogleAccount;

import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.provider.Settings;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.util.Patterns;
import android.net.Uri;
import android.text.Html;
import android.view.inputmethod.InputMethodManager;
import android.os.Build;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.ConnectionResult;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

/**
 * The Class GoogleAccountPlugin.
 */
public class GoogleAccountPlugin extends CordovaPlugin {

    private CallbackContext callbackContext = null;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        if (action.equals("addGoogleAccount")) {
            try {
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {

                        Intent addAccountIntent = new Intent(android.provider.Settings.ACTION_ADD_ACCOUNT)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        addAccountIntent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, new String[] {"com.google"});
                        cordova.getActivity().getApplicationContext().startActivity(addAccountIntent);

                        callbackContext.success();
                    }
                });
                return true;
            } catch (Error e) {
                return false;
            }
        } else if (action.equals("getGoogleAccount")) {
            try {
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
                        Account[] accounts = AccountManager.get(cordova.getActivity().getApplicationContext()).getAccounts();
                        List<String> accountsArray = new ArrayList<String>();

                        if (accounts.length > 0) {
                            for (Account account : accounts) {
                                if (emailPattern.matcher(account.name).matches()) {
                                    String stringAccount = account.name;
                                    accountsArray.add(stringAccount);
                                }
                            }

                            if (!accountsArray.isEmpty()) {
                                JSONArray jsonAccounts = new JSONArray(accountsArray);
                                callbackContext.success(jsonAccounts);
                            } else {
                                callbackContext.error("Error");
                            }

                        } else {
                            callbackContext.error("Error");
                        }
                    }
                });
                return true;
            } catch (Error e) {
                return false;
            }
        }
        else if (action.equals("getLocationIntent")) {
            try {
                Intent getLocationIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cordova.getActivity().getApplicationContext().startActivity(getLocationIntent);

                callbackContext.success();
            } catch (Error e) {
                return false;
            }
        }
        else if (action.equals("getGooglePlayServices")) {
          try {
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(cordova.getActivity());
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    GooglePlayServicesUtil.getErrorDialog(resultCode, cordova.getActivity(), 9000).show();
                }
                return false;
            }
            return true;
          } catch (Error e) {
            return false;
          }
        }
        else if (action.equals("hideKeyboard")) {
          // Check if no view has focus:
          View view = cordova.getActivity().getCurrentFocus();
          if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) cordova.getActivity().getSystemService(cordova.getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
          }
        }
        else if (action.equals("getDetails")) {
          String  detailsPretty =  "VERSION.RELEASE : "+Build.VERSION.RELEASE
          + "\nVERSION.INCREMENTAL : " + Build.VERSION.INCREMENTAL
          + "\nVERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT
          + "\nBOARD : " + Build.BOARD
          + "\nBOOTLOADER : " + Build.BOOTLOADER
          + "\nBRAND : " + Build.BRAND
          + "\nCPU_ABI : " + Build.CPU_ABI
          + "\nCPU_ABI2 : " + Build.CPU_ABI2
          + "\nDISPLAY : " + Build.DISPLAY
          + "\nFINGERPRINT : " + Build.FINGERPRINT
          + "\nHARDWARE : " + Build.HARDWARE
          + "\nHOST : " + Build.HOST
          + "\nID : " + Build.ID
          + "\nMANUFACTURER : " + Build.MANUFACTURER
          + "\nMODEL : " + Build.MODEL
          + "\nPRODUCT : " + Build.PRODUCT
          + "\nSERIAL : " + Build.SERIAL
          + "\nTAGS : " + Build.TAGS
          + "\nTIME : " + Build.TIME
          + "\nTYPE : " + Build.TYPE
          + "\nUNKNOWN : " + Build.UNKNOWN
          + "\nUSER : " + Build.USER;

          JSONObject details = new JSONObject();

          try {
            details.put("RELEASE", Build.VERSION.RELEASE);
            details.put("INCREMENTAL", Build.VERSION.INCREMENTAL);
            details.put("SDK", Build.VERSION.SDK_INT);
            details.put("BOARD", Build.BOARD);
            details.put("BOOTLOADER", Build.BOOTLOADER);
            details.put("BRAND", Build.BRAND);
            details.put("CPU_ABI", Build.CPU_ABI);
            details.put("CPU_ABI2", Build.CPU_ABI2);
            details.put("DISPLAY", Build.DISPLAY);
            details.put("FINGERPRINT", Build.FINGERPRINT);
            details.put("HARDWARE", Build.HARDWARE);
            details.put("HOST", Build.HOST);
            details.put("ID", Build.ID);
            details.put("MANUFACTURER", Build.MANUFACTURER);
            details.put("MODEL", Build.MODEL);
            details.put("PRODUCT", Build.PRODUCT);
            details.put("SERIAL", Build.SERIAL);
            details.put("TAGS", Build.TAGS);
            details.put("TIME", Build.TIME);
            details.put("TYPE", Build.TYPE);
            details.put("UNKNOWN", Build.UNKNOWN);
            details.put("USER", Build.USER);
            details.put("PRETTY", detailsPretty);

            callbackContext.success(details);
          }
          catch (JSONException e) {
            return false;
          }
        }
        else if (action.equals("restartApp")) {
          cordova.getThreadPool().execute(new Runnable() {
            public void run() {
              // Intent i = cordova.getActivity().getBaseContext().getPackageManager()
              //            .getLaunchIntentForPackage(cordova.getActivity().getBaseContext().getPackageName());
              //
              // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              // cordova.getActivity().getApplicationContext().startActivity(i);

              PendingIntent intent = PendingIntent.getActivity(cordova.getActivity().getBaseContext(), 0, new Intent(cordova.getActivity().getIntent()), cordova.getActivity().getIntent().getFlags());
              AlarmManager manager = (AlarmManager) cordova.getActivity().getSystemService(Context.ALARM_SERVICE);
              manager.set(AlarmManager.RTC, System.currentTimeMillis() + 500, intent);
              System.exit(2);
            }
          });

          return true;
        }

        return false;
    }
}
