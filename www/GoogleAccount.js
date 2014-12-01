/*
 * Googel Account
 * Implements the javascript access to the cordova plugin for adding a google account. Returns 0 if not running on Android
 * @author Olivier Brand
 */

/**
 * @return the google account class instance
 */
CK = ( typeof CK == 'undefined' ? {} : CK );
var cordova = window.cordova || window.Cordova;

CK.app = {

  addGoogleAccount: function(successCallback, failureCallback){
   return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
     'addGoogleAccount', []);
  },
  getGoogleAccount: function(successCallback, failureCallback){
   return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
     'getGoogleAccount', []);
  },
  getLocationIntent: function(successCallback, failureCallback){
   return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
     'getLocationIntent', []);
  },
  getGooglePlayServices: function(successCallback, failureCallback){
    return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
      'getGooglePlayServices', []);
  },
  hideKeyboard: function(successCallback, failureCallback){
    return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
    'hideKeyboard', []);
  }
};

module.exports = CK.app;
