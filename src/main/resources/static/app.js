angular.module('frontapp', []).controller('appController', function ($scope, $http) {
    $http.get('http://localhost:8189/app/product/json/2').then(function(response) {
        $scope.student = response.data;
    });

});