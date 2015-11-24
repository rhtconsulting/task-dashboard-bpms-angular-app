define([
        'angular',
        './controllers/myController',
        './controllers/testController',
        'angular-resource',
        'ng-table'
    ],
    function(angular, myController, testController) {
        'use strict';

        /**
         * @ngdoc object
         * @name bpmsTasklist
         * @description
         * Angular module that loads some BPMS tasks
         */
        var App = angular.module('bpmsTasklist', [
            'ngResource',
            'ngTable'
        ]);

        App.controller('myController', myController);
        App.controller('testController', testController);
    });
