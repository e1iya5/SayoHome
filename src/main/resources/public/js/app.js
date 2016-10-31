var SayoHome = angular.module('SayoHome', ['ngMaterial', 'ngRoute', 'shServices']);
SayoHome.config(function($routeProvider) {
  $routeProvider
  .when('/', {
    templateUrl: './partials/home.html',
    controller: "HomeCtrl"
  })
  .when('/dashboard', {
    templateUrl: './partials/dashboard.html',
    controller: "DashboardCtrl"
  })
  .when('/logs', {
    templateUrl: './partials/logs.html',
    controller: "LogsCtrl"
  })
  .when('/enviromentscripts', {
    templateUrl: './partials/enviromentscripts.html',
    controller: "EnviromentScriptsCtrl"
  })
  .when('/timerules', {
    templateUrl: './partials/timerules.html',
    controller: "TimeRulesCtrl"
  });
});

