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
  }//,
  // forceAppUpdate : function(params, success, fail) {
  //   return cordova.exec(function(args) {
  //       success(args);
  //   }, function(args) {
  //       fail(args);
  //   }, 'GoogleAccountPlugin', 'forceAppUpdate', [params]);
  // }
};

module.exports = CK.app;
