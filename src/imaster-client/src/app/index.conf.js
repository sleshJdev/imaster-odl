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
        .state('documents', {
            abstract: true,
            url: '/documents',
            template: '<ui-view/>'
        })
        .state('documents.list', {
            url: '/list',
            templateUrl: 'app/components/document/document-list.html',
            controller: 'DocumentListController',
            controllerAs: 'vm'
        })
        .state('documents.add', {
            url: '/add',
            templateUrl: 'app/components/document/document.html',
            controller: 'DocumentAddController',
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
            templateUrl: 'app/components/essay/essay-list.html',
            controller: 'EssayListController',
            controllerAs: 'vm'
        })
        .state('essays.add', {
            url: '/add',
            templateUrl: 'app/components/essay/essay.html',
            controller: 'EssayAddController',
            controllerAs: 'vm'
        })
        .state('essays.edit', {
            url: '/edit/:id',
            templateUrl: 'app/components/essay/essay.html',
            controller: 'EditEditController',
            controllerAs: 'vm',
            params: {id: null},
            resolve: {
                id: ['$stateParams', function ($stateParams) {
                    return $stateParams.id;
                }]
            }
        })
    ;
    $urlRouterProvider.otherwise('/');
    $httpProvider.interceptors.push('httpInterceptor');
}