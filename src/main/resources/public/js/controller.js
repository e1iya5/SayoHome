SayoHome.controller("MainCtrl", function ($scope, $rootScope, $location, session, $mdSidenav) {
    session.init();
    $scope.toggleSidenav = function () {
        $mdSidenav("shSidenav").toggle();
    };
    $rootScope.showSidenav = true;
    $rootScope.hideSidenav = function () {
        $scope.showSidenav = false;
    };
    $rootScope.showSidenav = function () {
        $scope.showSidenav = true;
    };
    $scope.openView = function (path) {
        $location.path(path);
    };
});

SayoHome.controller("HomeCtrl", function ($scope, $rootScope, $http, $location, session) {
    $rootScope.hideSidenav();
    $scope.login = function () {
        $http.post("./api/login", {
            username: $scope.username,
            pwd: $scope.pwd
        })
                .success(function (data, status, headers, config) {
                    session.setToken(data.token);
                    $location.path("dashboard");
                })
                .error(function (data, status, header, config) {

                });
    };
});

SayoHome.controller("DashboardCtrl", function ($scope, $rootScope, session) {
    $rootScope.showSidenav();
});

SayoHome.controller("LogsCtrl", function ($scope, $rootScope, $http, session) {
    $rootScope.showSidenav();
    $scope.entries = [];
    $http.get("./api/log/entries")
            .success(function (data, status, headers, config) {
                $scope.entries = data;
            })
            .error(function (data, status, header, config) {

            });
    $scope.formatDate = function(date){
        var d = new Date(date);
        return d.getDate()  + "." + (d.getMonth()+1) + "." + d.getFullYear() + " " + d.getHours() + ":" + d.getMinutes();
    }
});

SayoHome.controller("TimeRulesCtrl", function ($scope, $rootScope, $http, session) {
    $rootScope.showSidenav();
    $scope.rules = [];
    $http.get("./api/timerule/all")
            .success(function (data, status, headers, config) {
                $scope.rules = data;
            })
            .error(function (data, status, header, config) {

            });

    $scope.saveRule = function (rule) {
        console.log("save rule " + rule.id);
        $http.post("./api/timerule/"+rule.id+"/save", rule)
                .success(function (data, status, headers, config) {
                    
                })
                .error(function (data, status, header, config) {

                });
    }
});



