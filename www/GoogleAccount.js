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
    if (!successCallback) {
      successCallback = null;
    }

    if (!failureCallback) {
      failureCallback = null;
    }

    return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
    'hideKeyboard', []);
  },
  getDetails: function(successCallback, failureCallback){
    return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
    'getDetails', []);
  },
  restartApp: function(successCallback, failureCallback){
    if (!successCallback) {
      successCallback = null;
    }

    if (!failureCallback) {
      failureCallback = null;
    }

    return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
    'restartApp', []);
  },
  showApp: function(successCallback, failureCallback){
   return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
     'showApp', []);
  },
  showRunning: function(successCallback, failureCallback){
   return cordova.exec(successCallback, failureCallback, 'GoogleAccountPlugin',
     'showRunning', []);
  }
};

module.exports = CK.app;
