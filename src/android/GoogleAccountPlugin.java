package com.btarter.GoogleAccount;

import java.util.regex.Pattern;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.Context;
import android.provider.Settings;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.util.Patterns;

/**
 * The Class GoogleAccountPlugin.
 */
public class GoogleAccountPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

        if (action.equals("addGoogleAccount")) {
          try {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    // AccountManager acm = AccountManager.get(cordova.getActivity().getApplicationContext());
                    // acm.addAccount("com.google", null, null, null, null, null, null);

                    cordova.getActivity().getApplicationContext().startActivity(new Intent(Settings.ACTION_ADD_ACCOUNT).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

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
                    for (Account account : accounts) {
                        if (emailPattern.matcher(account.name).matches()) {
                            String possibleEmail = account.name;
                            callbackContext.success(possibleEmail); // Thread-safe.
                            // return true;
                        }
                    }
                }
            });
            return true;
          } catch (Error e) {
            return false;
          }
        }
        return false;
    }
}
