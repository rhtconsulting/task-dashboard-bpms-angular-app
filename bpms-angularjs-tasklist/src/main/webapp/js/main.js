(function($w) {
    'use strict';

    require.config({
        baseUrl: 'js/',
        paths: {
            text: 'vendor/text',
            jquery: 'vendor/jquery.min',
            angular: 'vendor/angular.min',
            'angular-resource': 'vendor/angular-resource.min',
            'ng-table': 'vendor/ng-table.min',
            bootstrap: 'vendor/bootstrap.min'
        },
        shim: {
            angular: {
                deps: ['jquery'],
                exports: 'angular'
            },
            'angular-resource': {
                deps: ['angular']
            },
            'ng-table': {
                deps: ['angular']
            },
            jquery: {
                exports: '$'
            },
            bootstrap: {
                deps: ['jquery']
            }
        },
        waitSeconds: 0
    });

    $w.$myapp = $w.$myapp || {};
    $w.$myapp.deps = $w.$myapp.deps || [];
    $w.$myapp.ngApps = $w.$myapp.ngApps || [];

    $w.$myapp.deps.push('angular');

    require($w.$myapp.deps, function() {
        angular.bootstrap(document, $w.$myapp.ngApps);
    });

})(window);
