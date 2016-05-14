/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */

require('angular');
require('angular-ui-router');

angular.module('imaster', ['ui.router'])
    .config([
        '$stateProvider', '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {
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
        }
    ])
    .run([
        'AuthService', '$state', '$log',
        function (AuthService, $state, $log) {
            'use strict';
            $log.debug('session interceptor was added');
            if (AuthService.isAnonymous()) {
                $log.debug('try restire token');
                AuthService.restore();
            }
        }
    ]);