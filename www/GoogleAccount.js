/*
 * Googel Account
 * Implements the javascript access to the cordova plugin for adding a google account. Returns 0 if not running on Android
 * @author Olivier Brand
 */

/**
 * @return the google account class instance
 */
 var GoogleAccount = {

   addGoogleAccount: function(successCallback, failureCallback){
     cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
       'addGoogleAccount', []);
   },
   getGoogleAccount: function(successCallback, failureCallback){
     cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
       'getGoogleAccount', []);
   }
 };

 module.exports = GoogleAccount;
