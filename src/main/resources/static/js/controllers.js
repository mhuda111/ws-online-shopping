(function(angular) {
  "use strict";
  
  angular.module("myApp.controllers").controller("RegisterCtrl", function($scope, $q) {
    
    $scope.passwordPattern = /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;
    
//    $scope.validUsername = function(username) {
//      var dfd = $q.defer();
//      setTimeout(function() {
//        dfd.resolve([ "g00glen00b", "Dimitri" ].indexOf(username) === -1);
//      }, 1000);
//      return dfd.promise;
//    };
    
  });
  
}(angular));