/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */

require('angular');
require('angular-ui-router');

angular.module('imaster', ['ui.router']).config([
    '$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        'use strict';
        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('/', {
                url: '/'
            })
            .state('users', {
                url: '/api/users',
                templateUrl: 'components/user/users.list.html',
                controller: 'UserController',
                controllerAs: 'vm'
            })
            .state('login', {
                url: '/api/login',
                templateUrl: 'components/login/login.html',
                controller: 'LoginController',
                controllerAs: 'vm'
            });
    }
]);