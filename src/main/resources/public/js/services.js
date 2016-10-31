var shServices = angular.module('shServices', []);
shServices.factory('session', function () {
    var s = {};
    var LS_KEY = "sayoHomeStorage";
    var getLocalStorage = function(){
        return localStorage[LS_KEY] ? JSON.parse(localStorage[LS_KEY]) : null;
    };
    var setLocalStorage = function(obj){
        localStorage[LS_KEY] = JSON.stringify(obj);
    };
    var getValue = function(key){
        return getLocalStorage()[key];
    };
    var setValue = function(key, val){
        var obj = getLocalStorage();
        obj["key"] = val;
        setLocalStorage(obj);
    };
    s.init = function () {
        if(!getLocalStorage()) setLocalStorage({});
    };
    s.setToken = function(val){
        setValue("token", val);
    }
    s.getToken = function(){
        return getValue("token");
    }
    return s;
});
