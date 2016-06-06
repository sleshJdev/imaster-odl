/**
 * @author yauheni.putsykovich
 */

angular
    .module('imaster')
    .service('authService', authService);

/** @ngInject*/
function authService($http, $q, $log) {
    'use strict';

    var self = {};

    self.authentication = null;
    self.isLogging = false;

    var service = {
        login: login,
        logout: logout,
        restore: restore,
        getAuthentication: getAuthentication,
        exportMethodsTo: exportMethodsTo
    };
    exportMethodsTo(service);

    return service;

    function exportMethodsTo(scope) {
        scope.isAuthenticated = isAuthenticated;
        scope.isAnonymous = isAnonymous;
        scope.getUserName = getUserName;
        scope.isStudent = isRole('student');
        scope.isTeacher = isRole('teacher');
        scope.isAdmin = isRole('admin');
        scope.isLogging = isLogging;
        scope.startLogging = startLogging;
        scope.endLogging = endLogging;
        scope.getToken = getToken;
    }

    function getToken() {
        return self.authentication ? self.authentication.token : undefined;
    }

    function setAuthentication(authData) {
        self.authentication = authData;
        $http.defaults.headers.common['X-Auth'] = getToken();
        if (sessionStorage) {
            if (authData) {
                sessionStorage.setItem("auth.data", JSON.stringify(authData));
            } else {
                sessionStorage.removeItem("auth.data");
            }
        }
    }

    function login(data) {
        return $http.post('/api/login', data)
            .then(function (response) {
                setAuthentication(response.data);
                return response;
            }, function (error) {
                setAuthentication(null);
                return $q.reject(error);
            });
    }

    function logout() {
        setAuthentication(null);
    }

    function restore() {
        if (sessionStorage) {
            var authData = sessionStorage.getItem("auth.data");
            if (authData) {
                try {
                    setAuthentication(JSON.parse(authData));
                    return true;
                } catch (e) {
                    $log.error(e);
                }
            }
        }
        return false;
    }

    function isAuthenticated() {
        return !isAnonymous();
    }

    function isAnonymous() {
        return !self.authentication;
    }

    function getUserName() {
        return !!self.authentication ? self.authentication.username : "empty";
    }

    function getAuthentication() {
        return angular.copy(self.authentication);
    }

    function hasRole(role) {
        return isAuthenticated() && !!self.authentication.roles && !!self.authentication.roles.length &&
            self.authentication.roles.indexOf(role) !== -1;
    }

    function isRole(role) {
        return function () {
            return hasRole(role);
        };
    }

    function isLogging() {
        return self.isLogging;
    }

    function startLogging() {
        self.isLogging = true;
    }

    function endLogging() {
        self.isLogging = false;
    }
}
