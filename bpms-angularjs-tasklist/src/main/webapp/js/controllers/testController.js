define([], function() {
    'use strict';

    /**
     * @ngdoc object
     * @name bpmsTasklist.controller:testController
     * @description Controller to provide some testing
     *
     */
    return ['$scope', '$http', function($scope, $http) {
        $scope.task = {
            name: 'testname',
            id: '1234'
        };

        var taskQueryUrl = '/business-central/rest/task/query?p=1&s=10'; //'rest/query/runtime/task/?p=1&s=10';

        var config = {
            headers: {
                'Accept': 'application/json',
                'Authorization': 'Basic amJvc3M6YnBtc3VpdGUxIQ=='
            }
        };

        var errorMessage = [{
            id: 'error occurred'
        }];
        $http.get(taskQueryUrl, config).then(function(response) {
            $scope.data = response.data.taskSummaryList ? response.data.taskSummaryList : errorMessage;
            console.log('hi!');
        }, function() {
            $scope.data = errorMessage;
            console.log('goodbye');
        })
    }];
});
