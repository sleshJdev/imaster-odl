/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */

require('angular');
require('angular-ui-router');

angular.module('imaster', ['ui.router'])
    .config([
        '$stateProvider', '$urlRouterProvider', '$httpProvider',
        function ($stateProvider, $urlRouterProvider, $httpProvider) {
            'use strict';
            $stateProvider
                .state('/', {
                    url: '/'
                })
                .state('students', {
                    url: '/api/students',
                    templateUrl: 'components/student/student.list.html',
                    controller: 'StudentController',
                    controllerAs: 'vm'
                })
                .state('student-add', {
                    url: '/api/students/add',
                    templateUrl: 'components/student/student.html',
                    controller: 'StudentAddController',
                    controllerAs: 'vm'
                })
                .state('login', {
                    url: '/api/login',
                    templateUrl: 'components/login/login.html',
                    controller: 'LoginController',
                    controllerAs: 'vm'
                });
            $urlRouterProvider.otherwise('/');
            $httpProvider.interceptors.push('httpInterceptor');
        }
    ])
    .run([
        'AuthService', '$state', '$log',
        function (AuthService, $state, $log) {
            'use strict';
            $log.debug('session interceptor was added');
            if (AuthService.isAnonymous()) {
                if (AuthService.restore()) {
                    $log.debug('restored token');
                } else {
                    $log.debug('token not found');
                }
            }
        }
    ]);