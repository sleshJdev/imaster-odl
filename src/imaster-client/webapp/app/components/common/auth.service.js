/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').service('AuthService', [
    '$http', '$q',
    function ($http, $q) {
        'use strict';
        var self = this;
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
            scope.getUserName = getUserName;
            scope.isStudent = isRole('student');
            scope.isTeacher = isRole('teacher');
            scope.isAdmin = isRole('admin');
            scope.isLogging = isLogging;
            scope.startLogging = startLogging;
            scope.endLogging = endLogging;
        }

        function setAuthentication(authData) {
            self.authentication = authData;
            $http.defaults.headers.common['X-Auth'] =
                authData ? authData.token : undefined;
            if (sessionStorage) {
                if (authData) {
                    sessionStorage.setItem("auth.data", JSON.stringify(authData));
                } else {
                    sessionStorage.removeItem("auth.data");
                }
            }
        }

        function login(username, password) {
            return $http.post('/api/login', {
                username: username,
                password: password
            }).then(function (response) {
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
                        console.log(e);
                    }
                }
            }
            return false;
        }

        function isAuthenticated() {
            return !!self.authentication;
        }

        function getUserName() {
            return !!self.authentication ? self.authentication.username : "empty";
        }

        function getAuthentication() {
            return angular.copy(self.authentication);
        }

        function hasRole(role) {
            return isAuthenticated() && !!self.authentication.roles &&
                !!self.authentication.roles.length &&
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
]);
