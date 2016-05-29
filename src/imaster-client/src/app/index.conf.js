/**
 * @author yauheni.putsykovich
 */

angular
    .module('imaster')
    .config(config);

/** @ngInject */
function config($stateProvider, $urlRouterProvider, $httpProvider) {
    'use strict';
    $stateProvider
        .state('/', {
            url: '/'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'app/components/login/login.html',
            controller: 'LoginController',
            controllerAs: 'vm'
        })
        .state('students', {
            abstract: true,
            url: '/students',
            template: '<ui-view/>'
        })
        .state('students.list', {
            url: '/',
            templateUrl: 'app/components/student/student-list.html',
            controller: 'StudentListController',
            controllerAs: 'vm'
        })
        .state('students.add', {
            url: '/add',
            templateUrl: 'app/components/student/student.html',
            controller: 'StudentAddController',
            controllerAs: 'vm'
        })
        .state('students.edit', {
            url: '/edit/:id',
            templateUrl: 'app/components/student/student.html',
            controller: 'StudentEditController',
            controllerAs: 'vm',
            params: {id: null},
            resolve: {
                id: ['$stateParams', function ($stateParams) {
                    return $stateParams.id;
                }]
            }
        })
        .state('essays', {
            abstract: true,
            url: '/essays',
            template: '<ui-view/>'
        })
        .state('essays.list', {
            url: '/',
            templateUrl: 'app/components/essay/essay.html',
            controller: 'EssayController',
            controllerAs: 'vm'
        })
        ;
    $urlRouterProvider.otherwise('/');
    $httpProvider.interceptors.push('httpInterceptor');
}