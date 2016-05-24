/**
 * @author yauheni.putsykovich
 */

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
                    templateUrl: 'app/components/student/student-list.html',
                    controller: 'StudentListController',
                    controllerAs: 'vm'
                })
                .state('student-add', {
                    url: '/api/students/add',
                    templateUrl: 'app/components/student/student.html',
                    controller: 'StudentAddController',
                    controllerAs: 'vm'
                })
                .state('student-edit', {
                    url: '/api/students/edit/:id',
                    templateUrl: 'app/components/student/student.html',
                    controller: 'StudentEditController',
                    controllerAs: 'vm',
                    params: {id: null},
                    resolve: {
                        id: ['$stateParams', function($stateParams) {
                            return $stateParams.id;
                        }]
                    }
                })
                .state('essays', {
                    url: '/api/essays',
                    templateUrl: 'app/components/essay/essay.html',
                    controller: 'EssayController',
                    controllerAs: 'vm'
                })
                .state('login', {
                    url: '/api/login',
                    templateUrl: 'app/components/login/login.html',
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