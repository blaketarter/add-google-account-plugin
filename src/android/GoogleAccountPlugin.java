package com.btarter.GoogleAccount;

import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

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
import android.net.Uri;
import android.text.Html;

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
                        // AccountManager acm = AccountManager.get(cordova.getActivity().getApplicationContext());
                        // acm.addAccount("com.google", null, null, null, null, null, null);

                        // cordova.getActivity().getApplicationContext().startActivity(new Intent(Settings.ACTION_ADD_ACCOUNT).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                        // callbackContext.success();


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
                                    // callbackContext.success(possibleEmail); // Thread-safe.
                                    // return true;
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
                // addAccountIntent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, new String[] {"com.google"});
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
        //   else if (action.equals("forceAppUpdate")) {
        //     // Parse the arguments
        //     JSONObject obj = args.getJSONObject(0);
        //     String type = obj.has("type") ? obj.getString("type") : null;
        //     Uri uri = obj.has("url") ? Uri.parse(obj.getString("url")) : null;
        //     JSONObject extras = obj.has("extras") ? obj.getJSONObject("extras") : null;
        //     Map<String, String> extrasMap = new HashMap<String, String>();
        //
        //     // Populate the extras if any exist
        //     if (extras != null) {
        //         JSONArray extraNames = extras.names();
        //         for (int i = 0; i < extraNames.length(); i++) {
        //             String key = extraNames.getString(i);
        //             String value = extras.getString(key);
        //             extrasMap.put(key, value);
        //         }
        //     }
        //
        //     try {
        //       forceActivity(obj.getString("action"), uri, type, extrasMap);
        //       callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
        //       return true;
        //     } catch (Error e) {
        //       return false;
        //     }
        // }
        return false;
    }

    // static final int FORCE_UPDATE_APP = 1; //request code
    //
    // public void forceActivity(String action, Uri uri, String type, Map<String, String> extras) {
    //
    //     Intent i = (uri != null ? new Intent(action, uri) : new Intent(action));
    //
    //     if (type != null && uri != null) {
    //         i.setDataAndType(uri, type); //Fix the crash problem with android 2.3.6
    //     } else {
    //         if (type != null) {
    //             i.setType(type);
    //         }
    //     }
    //
    //     i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //
    //     for (String key : extras.keySet()) {
    //         String value = extras.get(key);
    //         // If type is text html, the extra text must sent as HTML
    //         if (key.equals(Intent.EXTRA_TEXT) && type.equals("text/html")) {
    //             i.putExtra(key, Html.fromHtml(value));
    //         } else if (key.equals(Intent.EXTRA_STREAM)) {
    //             // allowes sharing of images as attachments.
    //             // value in this case should be a URI of a file
    //             i.putExtra(key, Uri.parse(value));
    //         } else if (key.equals(Intent.EXTRA_EMAIL)) {
    //             // allows to add the email address of the receiver
    //             i.putExtra(Intent.EXTRA_EMAIL, new String[] { value });
    //         } else {
    //             i.putExtra(key, value);
    //         }
    //     }
    //
    //     this.cordova.getActivity().startActivityForResult(i, FORCE_UPDATE_APP);
    //
    // }
    //
    // @Override
    // public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //     if (requestCode == FORCE_UPDATE_APP) {
    //         if (resultCode == Activity.RESULT_OK) {
    //         }
    //         if (resultCode == Activity.RESULT_CANCELED) {
    //         }
    //     }
    //
    //     super.onActivityResult(requestCode, resultCode, intent);
    // }
}
