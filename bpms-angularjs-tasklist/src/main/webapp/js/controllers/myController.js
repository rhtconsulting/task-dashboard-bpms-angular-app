define([], function() {
    'use strict';

    /**
     * @ngdoc object
     * @name bpmsTasklist.controller:myController
     * @description Controller to provide Server-side data display
     */
    return ['$scope', '$resource', 'ngTableParams',
        function($scope, $resource, NgTableParams) {
            var Api = $resource('/business-central/rest/task/query', {}, {
                tasks: {
                    method: 'GET',
                    isArray: false,
                    headers: {
                        Accept: 'application/json',
                        Authorization: 'Basic amJvc3M6YnBtc3VpdGUxIQ=='
                    }
                }
            });
            $scope.tableParams = new NgTableParams({
                page: 1, // show first page
                count: 10 // count per page
            }, {
            	counts : [10, 25, 50, 100, 1000],
                filterDelay: 750,
                getData: function(params) {
                    var errorMessage = [{
                            id: 'error occurred'
                        }],
                        paramString, filter, paramsObj;
                    if ($scope.selectedFilter) {
                        filter = $scope.selectedFilter;
                    } else {
                        /* This is a bad way to do this
                        We should really be extending and overriding ngTable behavior to do what we want */
                        paramString = JSON.stringify(params.filter());
                        filter = JSON.parse(paramString.replace(/filter\[|\]/g, ''));
                    }
                    paramsObj = angular.extend({}, {
                        p: params.page(),
                        s: params.count()
                    }, filter);
                    // ajax request to api
                    return Api.tasks(paramsObj).$promise.then(function(response) {
                        var data = response.taskSummaryList;
                        params.total(20000);
                        // set new data
                        return data;
                    }, function(response) {
                        params.total(1);
                        console.error('Failed: ' + response);
                        return errorMessage;
                    });
                }
            });
            $scope.filters = [{
                label: 'Ready Tasks',
                filter: {
                    status: 'ready'
                }
            }, {
                label: 'Completed Tasks',
                filter: {
                    status: 'completed'
                }
            }, {
                label: 'Process Instance Id is 201',
                filter: {
                    processInstanceId: 201
                }
            },{
                label: 'Id is 201, Status Ready',
                filter: {
                    processInstanceId: 201,
                    status: 'ready'
                }
            }];
        }
    ];
});
